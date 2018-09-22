package com.tregix.cryptocurrencytracker.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.fragments.CoinItemFragment;
import com.tregix.cryptocurrencytracker.fragments.IcoListFragment;
import com.tregix.cryptocurrencytracker.fragments.DashBoardFragment;
import com.tregix.cryptocurrencytracker.fragments.SignalsFragment;
import com.tregix.cryptocurrencytracker.fragments.TopNewsFragment;

public class MainActivity extends Fragment {

    private FragmentTransaction fragmentTransaction;
    private String tab = "coin";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(CoinItemFragment.newInstance(1));
                    tab = "coin";
                    return true;
                case R.id.navigation_favorite:
                    switchFragment(CoinItemFragment.newInstance(0));
                    tab = "coin";
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(DashBoardFragment.newInstance());
                    tab = "coin";
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(TopNewsFragment.newInstance(1,"cryptocurrency blockchain"));
                    tab = "news";
                    return true;
                case R.id.navigation_signals:
                    switchFragment(new IcoListFragment());
                    tab="signals";
                    return true;
                case R.id.navigation_blockfolio:
                    switchFragment(SignalsFragment.newInstance("3","Reviews"));
                    tab="reviews";
                    return true;
            }
            return false;
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchFragment(CoinItemFragment.newInstance(1));

       /* ((TextView)view.findViewById(R.id.search_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SearchActivity.class);
                intent.putExtra("name",tab);
                startActivity(intent);
            }
        });*/

        MobileAds.initialize(getActivity(), getString(R.string.ad_app_id));

        FirebaseMessaging.getInstance().subscribeToTopic("alert");

        return view;
    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }


}
