package com.tregix.cryptocurrencytracker.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        intent = new Intent(this, LoginActivity.class);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(!new SharedPreferenceUtil(SplashActivity.this).getBoolen(
                        SharedPreferenceUtil.ISUSERLOGGEDIN)) {
                    startActivity(new Intent(SplashActivity.this, WizardActivty.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, NavigationDrawerActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
