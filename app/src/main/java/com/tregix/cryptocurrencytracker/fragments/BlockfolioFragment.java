package com.tregix.cryptocurrencytracker.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregix.cryptocurrencytracker.Model.PortfolioItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.activities.BlockFolioAddCoinActivity;
import com.tregix.cryptocurrencytracker.adapter.MyPortfolioRecyclerViewAdapter;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;

import java.util.List;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getAllNews;
import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getUserPortfolio;

public class BlockfolioFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private FloatingActionButton addCoin;

    public static BlockfolioFragment newInstance() {
        BlockfolioFragment fragment = new BlockfolioFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_signals);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Blockfolio");

        addCoin = view.findViewById(R.id.add_coin);
        addCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),BlockFolioAddCoinActivity.class));
            }
        });
        // recyclerView.setAdapter(new NewsRecyclerViewAdapter(NewListContent.ITEMS., mListener));
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh_signals);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchData();
                    }
                }
        );
        //recyclerView.setAdapter(new SignalsRecyclerViewAdapter(Categories.ITEMS,mListener));
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isUserLoggedIn = SharedPreferenceUtil.getInstance(getActivity()).getBoolen(SharedPreferenceUtil.ISUSERLOGGEDIN);
        addCoin.setVisibility(isUserLoggedIn?View.VISIBLE:View.INVISIBLE);

        mySwipeRefreshLayout.setRefreshing(isUserLoggedIn);
        fetchData();
    }

    private void fetchData() {
        int uId = Utils.getUserId(getActivity());
        if(uId != -1)
        RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).getUserBlockfolio(uId).enqueue(getUserPortfolio(this));
        else
            showDialogLogin("Please login first!");
    }

    @Override
    public void onError() {
        super.onError();
        mySwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onPortfolioLoaded(List<PortfolioItem> item) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyPortfolioRecyclerViewAdapter(item, null));
        mySwipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }
}
