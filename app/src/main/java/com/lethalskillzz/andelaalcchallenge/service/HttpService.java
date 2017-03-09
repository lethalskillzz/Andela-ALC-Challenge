package com.lethalskillzz.andelaalcchallenge.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.lethalskillzz.andelaalcchallenge.dao.ProfileDataSource;
import com.lethalskillzz.andelaalcchallenge.dao.UserDataSource;
import com.lethalskillzz.andelaalcchallenge.model.ProfileItem;
import com.lethalskillzz.andelaalcchallenge.model.User;
import com.lethalskillzz.andelaalcchallenge.model.UserItem;
import com.lethalskillzz.andelaalcchallenge.network.rest.ApiClient;
import com.lethalskillzz.andelaalcchallenge.network.rest.ApiInterface;
import com.lethalskillzz.andelaalcchallenge.view.activity.ProfileActivity;
import com.lethalskillzz.andelaalcchallenge.view.activity.UserListActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.httpIntentLoadProfile;
import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.httpIntentLoadUsers;
import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.httpIntentLoadUsersError;

/**
 * Created by lethalskillzz on 06/03/2017.
 */

public class HttpService extends IntentService {

    private static String TAG = HttpService.class.getSimpleName();
    private UserDataSource userDataSource;
    private List<UserItem> userItems;

    private ProfileDataSource profileDataSource;
    private ProfileItem profileItem;

    private String profileUrl;

    public HttpService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        userDataSource = new UserDataSource(getApplicationContext());
        userItems = new ArrayList<>();

        profileDataSource = new ProfileDataSource(this);
        profileItem = new ProfileItem();

        if (intent != null) {
            int intentType = intent.getIntExtra("intent_type", 0);
            switch (intentType) {

                case httpIntentLoadUsers: {
                    loadGithubUsers();
                }
                break;

                case httpIntentLoadProfile: {
                    profileUrl = intent.getStringExtra("profile_url");
                    loadGithubProfile();
                }
                break;

            }
        }
    }


    private void loadGithubUsers() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Observable<User> call = apiService.getUsers();
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                        userDataSource.open();
                        userDataSource.clearAllUser();
                        userDataSource.createUser(userItems);
                        userDataSource.close();

                        sendMessage(httpIntentLoadUsers, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            Log.e(TAG, "Response Code: " + response.code());
                            Log.e(TAG, "Response Message: " + response.message());

                            sendMessage(httpIntentLoadUsersError, response.message());
                        }
                    }

                    @Override
                    public void onNext(User user) {
                        userItems = user.getItems();
                    }

                });
    }



    private void loadGithubProfile() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Observable<ProfileItem> call = apiService.getProfile(profileUrl);
        Subscription subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProfileItem>() {
                    @Override
                    public void onCompleted() {

                        profileDataSource.open();
                        profileDataSource.createProfile(profileItem);
                        profileDataSource.close();

                        sendMessage(httpIntentLoadProfile, null);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // cast to retrofit.HttpException to get the response code
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException) e;
                            Log.e(TAG, "Response Code: " + response.code());
                            Log.e(TAG, "Response Message: " + response.message());

                            sendMessage(httpIntentLoadUsersError, response.message());
                        }
                    }

                    @Override
                    public void onNext(ProfileItem item) {
                        profileItem = item;
                    }

                });
    }



    private void sendMessage(int id, String data) {


        switch (id) {

            case httpIntentLoadUsers: {

                Message msg = new Message();
                msg.what = httpIntentLoadUsers;
                msg.obj = data;

                if (UserListActivity.mUiHandler != null)
                    UserListActivity.mUiHandler.sendMessage(msg);
            }
            break;

            case httpIntentLoadProfile: {

                Message msg = new Message();
                msg.what = httpIntentLoadProfile;
                msg.obj = data;

                if (ProfileActivity.mUiHandler != null)
                    ProfileActivity.mUiHandler.sendMessage(msg);
            }
            break;

            case httpIntentLoadUsersError: {

                Message msg = new Message();
                msg.what = httpIntentLoadUsersError;
                msg.obj = data;

                if (UserListActivity.mUiHandler != null)
                    UserListActivity.mUiHandler.sendMessage(msg);
            }
            break;

        }
    }

}
