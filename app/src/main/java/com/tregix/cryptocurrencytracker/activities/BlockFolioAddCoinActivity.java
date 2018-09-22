package com.tregix.cryptocurrencytracker.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.adapter.CoinSeletctionAdapter;

public class BlockFolioAddCoinActivity extends BaseActivity {
    private ShimmerRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coin);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (ShimmerRecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hideShimmerAdapter();

        recyclerView.setAdapter(new CoinSeletctionAdapter(CoinListingContent.ITEMS, null, getIntent().getIntExtra
                ("type",getIntent().getIntExtra("type",1))));

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

                    recyclerView.setAdapter(new CoinSeletctionAdapter(CoinListingContent.
                            searchList(s.toString()), null, 1));
            }
        });

    }

    private void searchText(String msg) {
            recyclerView.setAdapter(new CoinSeletctionAdapter(CoinListingContent.
                    searchList(msg), null, getIntent().getIntExtra
                    ("type",getIntent().getIntExtra("type",1))));
    }

}
