package com.tregix.cryptocurrencytracker.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.R;

import im.delight.android.webview.AdvancedWebView;

public class WebviewActivity extends BaseActivity {

    private AdvancedWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        TextView title = findViewById(R.id.toolbar_title);
        title.setText(getIntent().getStringExtra("url"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        //  mWebView.setListener(this, this);
        mWebView.loadUrl(getIntent().getStringExtra("url"));

        mWebView.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                try {
                    if (mProgress == null) {
                        mProgress = new ProgressDialog(WebviewActivity.this);
                        mProgress.show();
                    }
                    mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
                    if (progress > 80) {
                        mProgress.dismiss();
                        mProgress = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
