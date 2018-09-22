package com.tregix.cryptocurrencytracker.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.Model.IcoItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.adapter.IcoItemRecyclerViewAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class IcoItemFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_TAB = "column-count";
    RecyclerView recyclerView;
    // TODO: Customize parameters
    private int tabType = 0;
    private OnListFragmentInteractionListener mListener;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IcoItemFragment() {
    }

    @Override
    public void onError() {
        super.onError();
        mySwipeRefreshLayout.setRefreshing(false);
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static IcoItemFragment newInstance(int columnCount) {
        IcoItemFragment fragment = new IcoItemFragment();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coinitem_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        view.findViewById(R.id.coin_list_header).setVisibility(View.GONE);

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

        fetchData();

        return view;
    }


    private void fetchData() {
        mySwipeRefreshLayout.setRefreshing(true);
            RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).
                    loadIcos(tabType).enqueue(RetrofitUtil.getIcos(this));
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

    @Override
    public void onIcosLoaded(List<IcoItem> coin) {
        recyclerView.setAdapter(new IcoItemRecyclerViewAdapter(coin, mListener));
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
