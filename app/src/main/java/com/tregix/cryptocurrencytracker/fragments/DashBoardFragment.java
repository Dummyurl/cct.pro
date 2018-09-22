package com.tregix.cryptocurrencytracker.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tregix.cryptocurrencytracker.Model.MarketCap;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.activities.SearchActivity;
import com.tregix.cryptocurrencytracker.utils.Utils;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getDashboardData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashBoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView marketCap;
    private TextView volume;
    private TextView btcDominance;
    private TextView activeAssets;
    private TextView activeMarkets;
    private TextView activeCurrencies;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private OnFragmentInteractionListener mListener;
    private DashBoardFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public DashBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DashBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashBoardFragment newInstance() {
        DashBoardFragment fragment = new DashBoardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        marketCap = view.findViewById(R.id.market_cap);
        volume = view.findViewById(R.id.volume);
        activeAssets = view.findViewById(R.id.active_assets);
        activeCurrencies = view.findViewById(R.id.btc_activee_currencies);
        activeMarkets = view.findViewById(R.id.active_markets);
        btcDominance = view.findViewById(R.id.btc_dominance);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

        mSectionsPagerAdapter = new DashBoardFragment.SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        if(mySwipeRefreshLayout != null){
            mySwipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            fetchData();
                        }
                    }
            );
            mySwipeRefreshLayout.setRefreshing(true);
        }

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mySwipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mySwipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        fetchData();

        try {

            AdView mAdView = (AdView) view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        }catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void fetchData() {
        RetrofitUtil.createProviderAPI(CryptoApi.BASE_URL).loadDashBoard().enqueue(getDashboardData(this));
    }

    @Override
    public void onMarketCapLoaded(MarketCap item) {
        super.onMarketCapLoaded(item);
        mySwipeRefreshLayout.setRefreshing(false);
        marketCap.setText("$" +Utils.getFormatedAmount(item.getTotalMarketCapUsd()));
        volume.setText("$" + Utils.getFormatedAmount(item.getTotal24hVolumeUsd()));
        activeAssets.setText(item.getActiveAssets()+"");
        activeCurrencies.setText(item.getActiveCurrencies()+"");
        activeMarkets.setText(item.getActiveMarkets()+"");
        btcDominance.setText(item.getBitcoinPercentageOfMarketCap()+"%");
    }


    @Override
    public void onError() {
        super.onError();
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            return CoinItemFragment.newInstance(CoinItemFragment.topVolTab);

            if(position == 1)
                return CoinItemFragment.newInstance(CoinItemFragment.GainerTab);

            if(position == 2)
                return CoinItemFragment.newInstance(CoinItemFragment.LoserTab);

            return  CoinItemFragment.newInstance(CoinItemFragment.topVolTab);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
