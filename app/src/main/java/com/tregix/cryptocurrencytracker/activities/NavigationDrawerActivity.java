package com.tregix.cryptocurrencytracker.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tregix.cryptocurrencytracker.BuildConfig;
import com.tregix.cryptocurrencytracker.Model.MarketCap;
import com.tregix.cryptocurrencytracker.Model.user.User;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.fragments.BlockfolioFragment;
import com.tregix.cryptocurrencytracker.fragments.DashBoardFragment;
import com.tregix.cryptocurrencytracker.fragments.IcoListFragment;
import com.tregix.cryptocurrencytracker.fragments.SignalsFragment;
import com.tregix.cryptocurrencytracker.fragments.TopNewsFragment;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;


public class NavigationDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentTransaction fragmentTransaction;
    public static NavigationDrawerActivity instance;
    private NavigationView navigationView;
    private static String appUrl = "https://play.google.com/store/apps/details?id=" ;

    private static int lastSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instance = this;
      //  marketCap = findViewById(R.id.market_cap);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUserInfo();
        switchFragment(new MainActivity());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void hideShowItems() {
        boolean isUserLoggedIn = SharedPreferenceUtil.getInstance(this).getBoolen(SharedPreferenceUtil.ISUSERLOGGEDIN);
        navigationView.getMenu().findItem(R.id.navigation_login).setVisible(!isUserLoggedIn);
        navigationView.getMenu().findItem(R.id.navigation_signup).setVisible(!isUserLoggedIn);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(isUserLoggedIn);

      //  navigationView.getMenu().findItem(lastSelectedTab).setChecked(true);

    }

    private void setUserInfo() {
        try {
            View header = navigationView.getHeaderView(0);

            ImageView imageView = header.findViewById(R.id.imageView);
            TextView name = header.findViewById(R.id.name);
            TextView email = header.findViewById(R.id.email);
            boolean isUserLoggedIn = SharedPreferenceUtil.getInstance(this).getBoolen(SharedPreferenceUtil.ISUSERLOGGEDIN);

            User user = SharedPreferenceUtil.getInstance(this).getUserObj();
            if (isUserLoggedIn && user != null
                    && user.getData() != null) {
                Glide.with(this)
                        .load(user.getData().getAvatar())
                        .into(imageView);
                name.setText(user.getData().getName());
                email.setText(user.getData().getEmail());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideShowItems();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
    @Override
    public void onMarketCapLoaded(MarketCap item) {
        super.onMarketCapLoaded(item);
     //   marketCap.setText("$" + Utils.getFormatedAmount(item.getTotalMarketCapUsd()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.navigation_home) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.navigation_login:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.navigation_signup:
                startActivity(new Intent(this,SignUpActivity.class));
                break;
            case R.id.navigation_home:
                lastSelectedTab = R.id.navigation_home;
                switchFragment(new MainActivity());
                break;
            case R.id.navigation_dashboard:
                lastSelectedTab = R.id.navigation_dashboard;

                switchFragment(DashBoardFragment.newInstance());
                break;

            case R.id.navigation_notifications:
                lastSelectedTab = R.id.navigation_notifications;

                switchFragment(TopNewsFragment.newInstance(1,"cryptocurrency blockchain"));
                break;

            case R.id.navigation_signals:
                lastSelectedTab = R.id.navigation_signals;

                switchFragment(new IcoListFragment());
                break;

            case R.id.navigation_blockfolio:
                lastSelectedTab = R.id.navigation_blockfolio;

                switchFragment(BlockfolioFragment.newInstance());
                break;
            case R.id.nav_setting:
                lastSelectedTab = R.id.nav_setting;
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.nav_share:
                shareApp();
                break;
            case R.id.nav_rate:
                showRateDialog(this);
                break;
            case R.id.navigation_site:
                openSite();
                break;
            case R.id.nav_logout:
                SharedPreferenceUtil.getInstance(this).logout();
                startActivity(new Intent(this,LoginActivity.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openSite() {
        Intent i = new Intent(this, WebviewActivity.class);
        i.putExtra("url", CryptoApi.CCT_BASE_URL);
        i.putExtra("title","Website");
        startActivity(i);
    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void shareApp() {

        String message = appUrl + BuildConfig.APPLICATION_ID;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Choose "));
    }

    public static void showRateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Rate application")
                .setMessage("Please, rate the app at play store")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context != null) {
                            String link = "market://details?id=";
                            try {
                                context.getPackageManager()
                                        .getPackageInfo("com.android.vending", 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                                link = appUrl;
                            }
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link + context.getPackageName())));
                        }
                    }
                })
                .setNegativeButton("CANCEL", null);
        builder.show();
    }
}
