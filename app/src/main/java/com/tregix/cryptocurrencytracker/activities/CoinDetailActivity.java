package com.tregix.cryptocurrencytracker.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.Model.WatchlistUpdate;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;
import com.tregix.cryptocurrencytracker.fragments.ChartFragment;
import com.tregix.cryptocurrencytracker.fragments.TopNewsFragment;

import java.util.ArrayList;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.saveUserWatchlist;

public class CoinDetailActivity extends BaseActivity {

    private TextView marketCap;
    private TextView volume;
    private TextView price;
    private TextView chg1h;
    private TextView chg24h;
    private TextView chg7d;
    private TextView title;
    private TextView supply;
    private CoinListingContent.CoinItem coin;
    private CoinDetailActivity.SectionsPagerAdapter mSectionsPagerAdapter;
    private AdView mAdView;
    private MenuItem mOptionsMenu;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        marketCap = findViewById(R.id.market_cap);
        volume =   findViewById(R.id.volume);
        price =   findViewById(R.id.coin_price);
        chg1h =   findViewById(R.id.chg_1h);
        chg24h =   findViewById(R.id.chg_24h);
        chg7d =   findViewById(R.id.chg_7d);
        title = findViewById(R.id.toolbar_title);
        supply = findViewById(R.id.supply);
        mSectionsPagerAdapter = new CoinDetailActivity.SectionsPagerAdapter(getSupportFragmentManager());
         mAdView = (AdView) findViewById(R.id.adView);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        coin = (CoinListingContent.CoinItem) getIntent().getSerializableExtra("coin");

        if(coin.getMarketCapUsd() != null)
        marketCap.setText("$" + Utils.truncateNumber(Double.parseDouble(coin.getMarketCapUsd())));

        if(coin.get24hVolumeUsd() != null)
        volume.setText("$" + Utils.truncateNumber(Double.parseDouble(coin.get24hVolumeUsd())));

        if(coin.getPriceUsd() != null)
        price.setText("$" + Utils.getFormatedAmount(Double.parseDouble(coin.getPriceUsd())));

        title.setText(coin.getName() + " - " + coin.getSymbol());

        if(coin.getAvailableSupply() != null)
        supply.setText(Utils.truncateNumber(Double.parseDouble(coin.getAvailableSupply()))/* + "/" +
                Utils.truncateNumber(Double.parseDouble(coin.getTotalSupply()))*/);

        if (coin.getPercentChange24h() != null)
           chg24h.setText( coin.getPercentChange24h() + "%");
        else{
            chg24h.setText("0%");
        }

        if (coin.getPercentChange1h() != null)
            chg1h.setText(  coin.getPercentChange1h() + "%");
        else{
            chg7d.setText("0%");
        }

        if (coin.getPercentChange7d() != null)
            chg7d.setText(  coin.getPercentChange7d() + "%");
        else{
            chg7d.setText("0%");
        }

        try{

            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
     }catch (Exception e) {
        e.printStackTrace();
    }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favt,menu);
        MenuItem item = menu.getItem(0);


        if(Utils.isFavorite(this,coin.getSymbol())){
            item.setIcon(android.R.drawable.star_on);
        }else{
            item.setIcon(android.R.drawable.star_off);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mOptionsMenu = item;
        int id = item.getItemId();

        if (id == R.id.favrt) {
            if(SharedPreferenceUtil.getInstance(this).getUserObj() != null
                    && SharedPreferenceUtil.getInstance(this).getUserObj().getData() != null) {
                showProgressDialog("Updating Favorites...");

                RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).saveWatchlist(SharedPreferenceUtil.getInstance(this).getUserObj().getData().getId()
                        ,coin.getSymbol().toLowerCase()).enqueue(saveUserWatchlist(this));
            }else{
                showDialogSignedUp("Please login first!");
            }
            return true;
        }

        if( id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onwatchlistUpdate(WatchlistUpdate update) {
        hideProgressDialog();
        super.onwatchlistUpdate(update);
        SharedPreferenceUtil pref = new SharedPreferenceUtil(this);
        ArrayList<String> list =  pref.loadTabs();

        if(list.contains(coin.getSymbol())){
            list.remove(coin.getSymbol());
            mOptionsMenu.setIcon(R.drawable.ic_star_empty);
        }else{
            list.add(coin.getSymbol());
            mOptionsMenu.setIcon(R.drawable.ic_star);
        }

        pref.storeFavorites(list);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0)
             return ChartFragment.newInstance(coin.getSymbol(),"");
            else
             return TopNewsFragment.newInstance(2,coin.getName());

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
