package com.tregix.cryptocurrencytracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.Model.NewListContent;
import com.tregix.cryptocurrencytracker.Model.cct.Categories;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.adapter.SignalsRecyclerViewAdapter;

import java.util.List;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getSignals;

public class SignalsFragment extends BaseFragment {
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String CATEGORY = "Category";
    public static final String TAB = "tab";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    String category;
    private String tabName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SignalsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SignalsFragment newInstance(String text,String name) {
        SignalsFragment fragment = new SignalsFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY, text);
        args.putString(TAB,name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
            tabName = getArguments().getString(TAB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signals, container, false);
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_signals);
        TextView title = toolbar.findViewById(R.id.tb_title);
        title.setText(tabName);
        // Set the adapter
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_signals);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (mColumnCount == 2) {
            view.findViewById(R.id.topbar).setVisibility(View.GONE);
        }else{
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        }
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
        mySwipeRefreshLayout.setRefreshing(true);
        fetchData();
        return view;
    }


    private void fetchData() {
        // RetrofitUtil.createProviderAPI(CryptoApi.NEWS_BASE_URL).loadTopNews().enqueue(getTopNews(this));
        RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).loadSignals(category).enqueue(getSignals(this));
    }

    @Override
    public void onError() {
        super.onError();
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSignalLoaded(List<Categories> item) {
        recyclerView.setAdapter(new SignalsRecyclerViewAdapter(item, mListener));
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        void onListFragmentInteraction(NewListContent.DummyItem item);
    }
}
