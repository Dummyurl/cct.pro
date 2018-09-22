package com.tregix.cryptocurrencytracker.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.tregix.cryptocurrencytracker.Model.Article;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.Model.Exchange;
import com.tregix.cryptocurrencytracker.Model.IcoItem;
import com.tregix.cryptocurrencytracker.Model.MarketCap;
import com.tregix.cryptocurrencytracker.Model.NewsItem;
import com.tregix.cryptocurrencytracker.Model.Pair;
import com.tregix.cryptocurrencytracker.Model.PortfolioItem;
import com.tregix.cryptocurrencytracker.Model.ResponseStatus;
import com.tregix.cryptocurrencytracker.Model.WatchlistItem;
import com.tregix.cryptocurrencytracker.Model.WatchlistUpdate;
import com.tregix.cryptocurrencytracker.Model.cct.Categories;
import com.tregix.cryptocurrencytracker.Model.singup.SignupData;
import com.tregix.cryptocurrencytracker.Model.user.User;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.OnDataLoadListener;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;
import com.tregix.cryptocurrencytracker.utils.ThemeUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.tregix.cryptocurrencytracker.utils.ThemeUtil.THEME_DEFAULT;

public class BaseActivity extends AppCompatActivity implements OnDataLoadListener,View.OnClickListener {

    private ProgressDialog progressDialog;
    public static int mTheme = THEME_DEFAULT;
    public static boolean mIsNightMode = true;
    public static int isCardView = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mIsNightMode){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        mTheme = new SharedPreferenceUtil(this).getIntValue(SharedPreferenceUtil.SELECTED_THEME) != -1?
                new SharedPreferenceUtil(this).getIntValue(SharedPreferenceUtil.SELECTED_THEME): THEME_DEFAULT;
        SettingActivity.selectedTheme = mTheme;

        isCardView = new SharedPreferenceUtil(this).getIntValue(SharedPreferenceUtil.ISCARDVIEWENABLED) != -1?
                new SharedPreferenceUtil(this).getIntValue(SharedPreferenceUtil.ISCARDVIEWENABLED): 0;

        setTheme(ThemeUtil.getThemeId(mTheme));
        progressDialog = new ProgressDialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    protected void showProgressDialog(String msg) {
        try {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage(msg);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
    }

    protected void openAcitivty(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void openAcitivty(Intent intent2, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        if (intent2 != null) {
            intent = intent2;
            intent.setClass(this, cls);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCoinLoaded(List<CoinListingContent.CoinItem> coin) {

    }

    @Override
    public void onNewsLoaded(NewsItem item) {

    }

    @Override
    public void onMarketCapLoaded(MarketCap item) {

    }

    @Override
    public void onGoogleNewsLoaded(List<Article> item) {

    }

    @Override
    public void onSignalLoaded(List<Categories> item) {

    }

    @Override
    public void onError() {
        hideProgressDialog();
        Utils.showError(this);
    }

    @Override
    public void onLoginAuthentication(Response<User> body) {

    }

    @Override
    public void onRegistration(Response<SignupData> registerDataResponse) {

    }

    @Override
    public void onNewsLoaded(List<Article> item) {

    }

    @Override
    public void onIcosLoaded(List<IcoItem> item) {

    }

    @Override
    public void onwatchlistLoaded(List<WatchlistItem> item) {

    }

    @Override
    public void onwatchlistUpdate(WatchlistUpdate update) {

    }

    @Override
    public void onPortfolioLoaded(List<PortfolioItem> item) {

    }

    @Override
    public void onExcahgnesLoaded(List<Exchange> item) {

    }

    @Override
    public void onTradingPairLoaded(List<Pair> item) {

    }

    @Override
    public void onCurrentPriceLoaded(Response<ResponseBody> item) {

    }

    @Override
    public void onBlockfolioUpdate(ResponseStatus item) {

    }

    protected void showDialogSignedUp(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setMessage(msg)
                .setPositiveButton(R.string.title_login, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                            openAcitivty(getIntent(), LoginActivity.class);
                            finish();
                        dialog.dismiss();

                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        }).setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }
}
