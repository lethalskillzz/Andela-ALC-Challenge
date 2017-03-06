package com.lethalskillzz.andelaalcchallenge.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lethalskillzz.andelaalcchallenge.R;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timeout
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        new Handler().postDelayed(new Runnable() {

            //Showing splash screen with a timer

            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, UserListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();

            }




        }, SPLASH_TIME_OUT);
    }
}
