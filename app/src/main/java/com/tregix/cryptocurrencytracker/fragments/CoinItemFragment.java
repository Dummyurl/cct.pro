package com.tregix.cryptocurrencytracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.Model.WatchlistItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.activities.SearchActivity;
import com.tregix.cryptocurrencytracker.adapter.MyCoinItemRecyclerViewAdapter;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getCoinListing;
import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getUserWatchlist;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CoinItemFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_TAB = "column-count";
    RecyclerView recyclerView;
    // TODO: Customize parameters
    private int tabType = 1;
    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private static String selectedCurrency = "USD";

    public static int favtTab = 0;
    public static int usdTab = 1;
    public static int btcTab = 2;
    public static int topVolTab = 4;
    public static int GainerTab = 5;
    public static int LoserTab = 6;

    private TextView sort24h;
    private TextView sort1h;

    Toolbar toolbar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CoinItemFragment() {
    }

    @Override
    public void onError() {
        super.onError();
        mySwipeRefreshLayout.setRefreshing(false);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CoinItemFragment newInstance(int columnCount) {
        CoinItemFragment fragment = new CoinItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tabType = getArguments().getInt(ARG_TAB);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coinitem_list, container, false);


        sort24h = view.findViewById(R.id.sort_24h);
        sort1h = view.findViewById(R.id.sort_1h);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Markets");

        if (tabType != topVolTab && tabType != GainerTab && tabType != LoserTab) {
            sort1h.setOnClickListener(this);
            sort24h.setOnClickListener(this);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchData();
                    }
                }
        );

        Spinner spinner = view.findViewById(R.id.currency);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCurrency = adapterView.getItemAtPosition(i).toString();
                fetchData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        if (tabType == favtTab) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.getFavorite(getActivity()),
                    mListener, tabType));
        } else
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.ITEMS, mListener, tabType));


        fetchData();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && tabType == favtTab) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.getFavorite(getActivity()),
                    mListener, tabType));
        }

        super.setUserVisibleHint(isVisibleToUser);

    }

    private void resetSelections() {
        if (getActivity() != null) {
            if (sort24h != null) {
                sort24h.setSelected(false);
                sort24h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
                sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
            if (sort1h != null) {
                sort1h.setSelected(false);
                sort1h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
                sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.sort_24h:
                sort24h();
                return;
            case R.id.sort_1h:
                sort1h();
                return;
        }

        super.onClick(view);

    }

    private void sort1h() {
        sort1h.setTextColor(getActivity().getResources().getColor(R.color.selectionColor));
        if (sort1h.isSelected()) {
            sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
            sort1h.setSelected(false);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sort1hDescending(), mListener, tabType));
        } else {
            sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
            sort1h.setSelected(true);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sort1hAscending(), mListener, tabType));
        }
        sort24h.setSelected(false);
        sort24h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void sort24h() {
        sort24h.setTextColor(getActivity().getResources().getColor(R.color.selectionColor));
        if (sort24h.isSelected()) {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
            sort24h.setSelected(false);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sort24hDecending(), mListener, tabType));
        } else {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
            sort24h.setSelected(true);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sort24hAscending(), mListener, tabType));
        }
        sort1h.setSelected(false);
        sort1h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void sort7d() {
        sort24h.setTextColor(getActivity().getResources().getColor(R.color.selectionColor));
        if (sort24h.isSelected()) {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
            sort24h.setSelected(false);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sort7dDecending(), mListener, tabType));
        } else {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
            sort24h.setSelected(true);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sort7dAscending(), mListener, tabType));
        }
        sort1h.setSelected(false);
        sort1h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void sortVol() {
        if (sort24h.isSelected()) {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
            sort24h.setSelected(false);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sortVol24hDecending(), mListener, tabType));
        } else {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
            sort24h.setSelected(true);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sortVolAscending(), mListener, tabType));
        }
        sort1h.setSelected(false);
        sort1h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void sortMarketCap() {
        if (sort24h.isSelected()) {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_downward, 0);
            sort24h.setSelected(false);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.sortMCDecending(), mListener, tabType));
        } else {
            sort24h.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_upward, 0);
            sort24h.setSelected(true);
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter
                    (CoinListingContent.getMarketCapUsd(), mListener, tabType));
        }
        sort1h.setSelected(false);
        sort1h.setTextColor(getActivity().getResources().getColor(android.R.color.darker_gray));
        sort1h.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_view, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("name", "coin");
                intent.putExtra("type", tabType);
                startActivity(intent);
                return true;
            case R.id.sort1h:
                sort1h();
                return true;
            case R.id.sort24h:
                sort24h();
                return true;
            case R.id.sort7d:
                sort7d();
                return true;
            case R.id.sortvol:
                sortVol();
                return true;
            case R.id.sortmc:
                sortMarketCap();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        mySwipeRefreshLayout.setRefreshing(false);
        if (tabType == topVolTab) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.topVolume(), mListener, tabType));
        } else if (tabType == GainerTab) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.topGainers(), mListener, tabType));
        } else if (tabType == LoserTab) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.topLossers(), mListener, tabType));
        } else if (tabType == favtTab) {
            if (SharedPreferenceUtil.getInstance(getActivity()).getUserObj() != null
                    && SharedPreferenceUtil.getInstance(getActivity()).getUserObj().getData() != null) {
                mySwipeRefreshLayout.setRefreshing(true);
                RetrofitUtil.createProviderAPI(CryptoApi.CCT_BASE_URL).
                        getUserWatchlist(SharedPreferenceUtil.getInstance(getActivity()).getUserObj().getData().getId()).enqueue(getUserWatchlist(this));
            } else {
                showDialogLogin(getString(R.string.login_to_view_watchlist));
            }
        } else {
            mySwipeRefreshLayout.setRefreshing(true);
            RetrofitUtil.createProviderAPI(CryptoApi.BASE_URL).
                    loadCoinsListing(selectedCurrency).enqueue(getCoinListing(this));
        }
    }

    @Override
    public void onwatchlistLoaded(List<WatchlistItem> coin) {
        ArrayList<String> coins = new ArrayList<>();

        for (WatchlistItem item : coin) {
            coins.add(item.getCoin().toLowerCase());
        }

        SharedPreferenceUtil.getInstance(getActivity()).storeFavorites(coins);

        recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.getFavorite(getActivity(), coins), mListener, tabType));
        mySwipeRefreshLayout.setRefreshing(false);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCoinLoaded(List<CoinListingContent.CoinItem> coin) {
        resetSelections();

        if (tabType == favtTab) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.getFavorite(getActivity()), mListener, tabType));

        } else {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(coin, mListener, tabType));
        }
        mySwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(CoinListingContent.CoinItem item);
    }
}
