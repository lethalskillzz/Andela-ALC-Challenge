package com.lethalskillzz.andelaalcchallenge.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lethalskillzz.andelaalcchallenge.R;
import com.lethalskillzz.andelaalcchallenge.dao.UserDataSource;
import com.lethalskillzz.andelaalcchallenge.model.UserItem;
import com.lethalskillzz.andelaalcchallenge.network.ConnectionDetector;
import com.lethalskillzz.andelaalcchallenge.view.adapter.UserItemAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.lethalskillzz.andelaalcchallenge.manager.AppConfig.handlerClickUser;

public class UserListActivity extends AppCompatActivity {


    private static final String TAG = UserListActivity.class.getSimpleName();
    private ConnectionDetector cd;

    private UserDataSource userDataSource;
    private List<UserItem> userItems;

    public static Handler mUiHandler;


    private RecyclerView rView;
    private UserItemAdapter userItemAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;



    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_list_toolbar);
        setSupportActionBar(toolbar);

        // creating connection detector class instance
        cd = new ConnectionDetector(this);

        userDataSource = new UserDataSource(this);
        userItems = new ArrayList<>();

        LinearLayoutManager lLayout = new LinearLayoutManager(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.user_list_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        rView = (RecyclerView) findViewById(R.id.user_list_recycler_view);
        rView.setLayoutManager(lLayout);
        rView.setHasFixedSize(true);

        setupUserItemAdapter();


        mUiHandler = new Handler() {
            public void handleMessage(Message msg) {

                String data[] = ((String)msg.obj).trim().split(":");
                switch(msg.what) {
                    case handlerClickUser: {

                        String id = data[0];
                        String login = data[1];
                        String avatarUrl = data[2];
                        String url = data[3];

                        Intent intent = new Intent(UserListActivity.this, ProfileActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("login", login);
                        intent.putExtra("avatarUrl", avatarUrl);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                    break;
                }
            }

        };

    }


    @Override
    public void onResume() {
        super.onResume();

        loadUserOffline();
    }

    private void setupUserItemAdapter() {
        userItemAdapter = new UserItemAdapter(this, userItems);
        rView.setAdapter(userItemAdapter);

    }

    private void loadUserOffline() {

        userDataSource.open();
        List<UserItem> items = userDataSource.readAllUser();
        userDataSource.close();

        userItems.clear();
        for (UserItem item: items) {
            userItems.add(item);
        }

        userItemAdapter.notifyDataSetChanged();

    }


    //SnackBar function
    private void showSnackBar(String msg) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.user_list_coordinator_layout);
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
