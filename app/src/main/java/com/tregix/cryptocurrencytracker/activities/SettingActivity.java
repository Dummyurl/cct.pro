package com.tregix.cryptocurrencytracker.activities;

import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.tregix.cryptocurrencytracker.Model.Theme;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.adapter.RecyclerViewClickListener;
import com.tregix.cryptocurrencytracker.adapter.ThemeAdapter;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;
import com.tregix.cryptocurrencytracker.utils.ThemeUtil;
import com.tregix.cryptocurrencytracker.utils.ThemeView;

import java.util.ArrayList;
import java.util.List;

import static com.tregix.cryptocurrencytracker.activities.BaseActivity.isCardView;
import static com.tregix.cryptocurrencytracker.activities.BaseActivity.mIsNightMode;

public class SettingActivity extends BaseActivity {

    public static List<Theme> mThemeList = new ArrayList<>();
    public static int selectedTheme = 0;
    private RecyclerView mRecyclerView;
    private ThemeAdapter mAdapter;
    private BottomSheetBehavior mBottomSheetBehavior;
    SwitchCompat switchCompat,cardView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initBottomSheet();

        prepareThemeData();

        ThemeView themeView = findViewById(R.id.theme_selected);
        themeView.setTheme(mThemeList.get(selectedTheme));

        switchCompat.setChecked(mIsNightMode);
        cardView.setChecked(isCardView == 1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }



    private void initBottomSheet(){
        // get the bottom sheet view
        LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        // init the bottom sheet behavior
        mBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        switchCompat = findViewById(R.id.switch_dark_mode);
        mRecyclerView = findViewById(R.id.recyclerView);
        cardView = findViewById(R.id.switch_card_voew);
        setListener();

        mAdapter = new ThemeAdapter(mThemeList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                new SharedPreferenceUtil(SettingActivity.this).storeIntValue(SharedPreferenceUtil.SELECTED_THEME,position);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SettingActivity.this.recreate();
                        NavigationDrawerActivity.instance.recreate();

                    }
                },400);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListener() {
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mIsNightMode = b;
                int delayTime = 200;
                if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    delayTime = 400;
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                compoundButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mIsNightMode){
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }else{
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        NavigationDrawerActivity.instance.recreate();

                    }
                },delayTime);

                new SharedPreferenceUtil(SettingActivity.this).storeBooleanValue(SharedPreferenceUtil.ISDARKMODEENABLED,mIsNightMode);

            }
        });

        cardView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCardView = b ? 1 : 0;
                new SharedPreferenceUtil(SettingActivity.this).storeIntValue(SharedPreferenceUtil.ISCARDVIEWENABLED,isCardView);

            }
        });
    }

    private void prepareThemeData() {
        mThemeList.clear();
        mThemeList.addAll(ThemeUtil.getThemeList());
        mAdapter.notifyDataSetChanged();
    }

}
