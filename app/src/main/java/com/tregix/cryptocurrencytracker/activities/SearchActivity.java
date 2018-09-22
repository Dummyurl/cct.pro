package com.tregix.cryptocurrencytracker.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.tregix.cryptocurrencytracker.Model.Article;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;
import com.tregix.cryptocurrencytracker.adapter.MyCoinItemRecyclerViewAdapter;
import com.tregix.cryptocurrencytracker.adapter.NewsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import me.toptas.rssconverter.RssFeed;
import me.toptas.rssconverter.RssItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {
    ShimmerRecyclerView recyclerView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (ShimmerRecyclerView) findViewById(R.id.list);
        name = getIntent().getStringExtra("name");

        recyclerView.hideShimmerAdapter();

        if (!name.contains("coin")) {
            findViewById(R.id.header_view).setVisibility(View.GONE);
        }

        TextView.OnEditorActionListener EnterOnText = new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String msg = view.getText().toString();
                    if (!msg.isEmpty()) {
                        searchText(msg);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                return true;
            }
        };
        ((EditText) findViewById(R.id.search_text)).setOnEditorActionListener(EnterOnText);
        ((EditText) findViewById(R.id.search_text)).addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 5)
                    searchText(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (name.contains("coin")) {
                    recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.
                            searchList(s.toString()), null, 1));
                }
            }
        });

    }

    private void searchText(String msg) {
        if (name.contains("coin")) {
            recyclerView.setAdapter(new MyCoinItemRecyclerViewAdapter(CoinListingContent.
                    searchList(msg), null, getIntent().getIntExtra
                    ("type",getIntent().getIntExtra("type",1))));
        } else {
            getAllNews(msg);
        }
    }


    private void getAllNews(String key) {
        recyclerView.showShimmerAdapter();
        CryptoApi service = RetrofitUtil.createGoogleNewsApi(CryptoApi.GOOGLE_NEWS_BASE_URL);
        service.getRss("https://news.google.com/news/rss/search/section/q/" + key)
                .enqueue(new Callback<RssFeed>() {
                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                        List<Article> list = new ArrayList<>();
                        for (RssItem item : response.body().getItems()) {
                            Article article = new Article();
                            article.setTitle(item.getTitle());
                            article.setDescription(Html.fromHtml(item.getDescription()).toString());
                            article.setUrlToImage(item.getImage());
                            article.setUrl(item.getLink());
                            article.setPublishedAt(item.getPublishDate());
                            list.add(article);
                        }
                        recyclerView.hideShimmerAdapter();
                        recyclerView.setAdapter(new NewsRecyclerViewAdapter(list, null));
                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        recyclerView.hideShimmerAdapter();
                        Log.d("error", t.getLocalizedMessage());
                        Utils.showError(SearchActivity.this);
                    }
                });
    }
}
