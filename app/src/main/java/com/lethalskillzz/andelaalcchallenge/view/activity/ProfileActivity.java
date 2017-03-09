package com.lethalskillzz.andelaalcchallenge.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lethalskillzz.andelaalcchallenge.R;
import com.lethalskillzz.andelaalcchallenge.dao.ProfileDataSource;
import com.lethalskillzz.andelaalcchallenge.model.ProfileItem;
import com.lethalskillzz.andelaalcchallenge.network.ConnectionDetector;
import com.lethalskillzz.andelaalcchallenge.service.HttpService;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.GET_PROFILE;
import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.httpIntentLoadProfile;

public class ProfileActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final String TAG = ProfileActivity.class.getSimpleName();
    private ConnectionDetector cd;

    private ProfileDataSource profileDataSource;
    private ProfileItem profileItem;

    public static Handler mUiHandler;

    private String id;
    private String login;
    private String avatarUrl;
    private String url;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;



    private LinearLayout mTitleContainer;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private CircleImageView profileImage;
    private TextView textTitle;
    private TextView textName;
    private TextView textLogin;
    private TextView textRepo;
    private TextView textFollowing;
    private TextView textFollowers;
    private TextView textUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        login = intent.getStringExtra("login");
        avatarUrl = intent.getStringExtra("avatarUrl");
        url = intent.getStringExtra("url");

        // creating connection detector class instance
        cd = new ConnectionDetector(this);

        profileDataSource = new ProfileDataSource(this);
        profileItem = new ProfileItem();

        bindActivity();

        textTitle.setText(login);
        textLogin.setText(login);
        textUrl.setText(url);


        Glide.with(getApplicationContext()).load(avatarUrl).asBitmap().centerCrop().into(new BitmapImageViewTarget(profileImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                profileImage.setImageDrawable(circularBitmapDrawable);
            }
        });

        mUiHandler = new Handler() {
            public void handleMessage(Message msg) {


                switch(msg.what) {
                    case httpIntentLoadProfile: {

                        displayProfile();
                    }
                    break;
                }
            }

        };

        bindActivity();

        mAppBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(textTitle, 0, View.INVISIBLE);

        displayProfile();
        loadProfile();
    }


    private void loadProfile() {

        if (cd.isConnectingToInternet()) {

            Intent httpIntent = new Intent(ProfileActivity.this, HttpService.class);
            httpIntent.putExtra("intent_type", httpIntentLoadProfile);
            httpIntent.putExtra("profile_url", GET_PROFILE+login);
            startService(httpIntent);

        } else {
            showSnackBar(getString(R.string.error_no_internet));
        }
    }

    private void displayProfile() {

        profileDataSource.open();
        profileItem = profileDataSource.readProfileByLogin(login);
        profileDataSource.close();

        textName.setText(profileItem.getName());
        textRepo.setText(profileItem.getPublicRepos());
        textFollowing.setText(profileItem.getFollowing());
        textFollowers.setText(profileItem.getFollowers());
    }

    private void bindActivity() {
        profileImage    = (CircleImageView) findViewById(R.id.profile_image);
        mToolbar        = (Toolbar) findViewById(R.id.profile_toolbar);
        mTitleContainer = (LinearLayout) findViewById(R.id.profile_linearLayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.profile_appbar);
        textTitle       = (TextView) findViewById(R.id.profile_text_title);
        textName        = (TextView) findViewById(R.id.profile_text_name);
        textLogin       = (TextView) findViewById(R.id.profile_text_login);
        textRepo        = (TextView) findViewById(R.id.profile_text_repo);
        textFollowing   = (TextView) findViewById(R.id.profile_text_following);
        textFollowers   = (TextView) findViewById(R.id.profile_text_followers);
        textUrl         = (TextView) findViewById(R.id.profile_text_url);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(textTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(textTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }



    //SnackBar function
    private void showSnackBar(String msg) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.profile_coordinator_layout);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
