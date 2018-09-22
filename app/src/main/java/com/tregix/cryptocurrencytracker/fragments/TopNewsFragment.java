package com.tregix.cryptocurrencytracker.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.tregix.cryptocurrencytracker.Model.Article;
import com.tregix.cryptocurrencytracker.Model.NewsItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.Model.NewListContent.DummyItem;
import com.tregix.cryptocurrencytracker.activities.SearchActivity;
import com.tregix.cryptocurrencytracker.adapter.NewsRecyclerViewAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TopNewsFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String SEARCH_STRING = "SearchString";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    String searchText;

    LinearLayoutManager layoutManager ;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopNewsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TopNewsFragment newInstance(int columnCount, String text) {
        TopNewsFragment fragment = new TopNewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(SEARCH_STRING, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            searchText = getArguments().getString(SEARCH_STRING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        setHasOptionsMenu(true);

        if(mColumnCount !=2 )
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("News");

        // Set the adapter
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        // recyclerView.setAdapter(new NewsRecyclerViewAdapter(NewListContent.ITEMS., mListener));
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        fetchData();
                    }
                }
        );
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(NewsItem.ITEMS, mListener));
        mySwipeRefreshLayout.setRefreshing(true);
        fetchData();
        return view;
    }

   /* private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    loadMoreItems();
                }
            }
        }
    };*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            Intent intent = new Intent(getActivity(),SearchActivity.class);
            intent.putExtra("name","news");
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        if(mColumnCount == 2){
            RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).getCoinNews(searchText).enqueue(RetrofitUtil.getAllNews(this));
        }else {
            RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).loadNews().enqueue(RetrofitUtil.getAllNews(this));
        }
    }

    @Override
    public void onError() {
        super.onError();
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNewsLoaded(List<Article> item) {
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(item, mListener));
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGoogleNewsLoaded(List<Article> item) {
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(item, mListener));
        mySwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNewsLoaded(NewsItem item) {
        super.onNewsLoaded(item);
        recyclerView.setAdapter(new NewsRecyclerViewAdapter(item.getArticles(), mListener));
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
        void onListFragmentInteraction(DummyItem item);
    }
}
