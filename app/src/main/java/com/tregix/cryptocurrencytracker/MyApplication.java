package com.tregix.cryptocurrencytracker;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;

import static com.tregix.cryptocurrencytracker.activities.BaseActivity.mIsNightMode;

/**
 * Created by Pankaj on 11-10-2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        mIsNightMode = new SharedPreferenceUtil(this).isNightMode();
        if(mIsNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        Utils.setAlarm(this);

    }
}
