package com.tregix.cryptocurrencytracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tregix.cryptocurrencytracker.Model.BlockfolioUpdateItem;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.Model.Exchange;
import com.tregix.cryptocurrencytracker.Model.Pair;
import com.tregix.cryptocurrencytracker.Model.ResponseStatus;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getCoinCurrentPrice;
import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getCoinPairs;
import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getExchanges;
import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.updateUserBlockfolio;

public class AddTransactionActivity extends BaseActivity {
    private TextView btn1;
    private TextView btn2,currentPrice,totalVal;

    private RecyclerView rv;
    private RadioGroup rg;
    private RadioButton rbuy, rsell, rwatch;
    private Button submit;
    public static final int REQUEST_CODE_PAIR = 100;
    public static final int REQUEST_CODE_EXCHANGE = 101;
    private CoinListingContent.CoinItem coin;
    private ArrayList<String> exchanges;

    private ArrayList<String> tradingPairs;
    private EditText holding;

    private int transactionType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn1 = (TextView) findViewById(R.id.button3);
        btn2 = (TextView) findViewById(R.id.button4);
        rg = (RadioGroup) findViewById(R.id.radiogroup);
        rbuy = (RadioButton) findViewById(R.id.buy);
        rsell = (RadioButton) findViewById(R.id.sell);
        rwatch = (RadioButton) findViewById(R.id.watchonly);
        currentPrice = findViewById(R.id.current_price);
        totalVal = findViewById(R.id.total_val);
        submit = findViewById(R.id.submit);

        exchanges = new ArrayList<>();
        tradingPairs = new ArrayList<>();

        rbuy.setOnClickListener(this);
        rsell.setOnClickListener(this);
        rwatch.setOnClickListener(this);

        holding = ((EditText) findViewById(R.id.holding));

        coin = (CoinListingContent.CoinItem) getIntent().getSerializableExtra("coin");

        showProgressDialog("Loading Exchanges...");

        RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).getExchange(coin.getSymbol()).enqueue(getExchanges(this));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(AddTransactionActivity.this, SelectableItemActivity.class);
                newIntent.putStringArrayListExtra("Data", tradingPairs);
                newIntent.putExtra("selectedItem",btn1.getText().toString());
                startActivityForResult(newIntent, REQUEST_CODE_PAIR);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(AddTransactionActivity.this, SelectableItemActivity.class);
                newIntent.putStringArrayListExtra("Data", exchanges);
                newIntent.putExtra("selectedItem",btn2.getText().toString());
                startActivityForResult(newIntent, REQUEST_CODE_EXCHANGE);
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exchange = btn2.getText().toString();
                String pair = btn1.getText().toString();
                String quantity = holding.getText().toString();
                String price = currentPrice.getText().toString();

                BlockfolioUpdateItem item = new BlockfolioUpdateItem(coin.getSymbol(),exchange,pair,quantity,price,transactionType);

                showProgressDialog("Updating Blockfolio...");
                RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL)
                        .updateUserPortfolio(Utils.getUserId(AddTransactionActivity.this),item).enqueue(updateUserBlockfolio(AddTransactionActivity.this));
            }

        });

        holding.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                double val = Double.parseDouble(currentPrice.getText().toString());

                if(s != null &&  s.length() > 0) {
                    double holding = Double.parseDouble(s.toString());

                    totalVal.setText(val * holding + "");
                }else{
                    totalVal.setText("0");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                double val = Double.parseDouble(currentPrice.getText().toString());

                if(s != null &&  s.length() > 0) {
                    double holding = Double.parseDouble(s.toString());

                    totalVal.setText(val * holding + "");
                }else{
                    totalVal.setText("0");
                }

            }
        });
    }

    public void onClick(View v) {
        int checkedRadioButtonId = rg.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.buy:
                if (rbuy.isChecked()) {
                    transactionType = 1;
                }
                break;
            case R.id.sell:
                if (rsell.isChecked()) {
                    transactionType = 2;
                }
                break;
            case R.id.watchonly:
                if (rwatch.isChecked()) {
                    transactionType = 3;
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PAIR) {
                List categoryList = data.getStringArrayListExtra("Data");
                if(categoryList != null && !categoryList.isEmpty()) {
                    String name = categoryList.get(0).toString();
                    btn1.setText(name);

                 String[] split = name.split("/");
                 RetrofitUtil.cctAuthentication(CryptoApi.CURRENT_PRICE_BASE_URL).getCurrentPrice(coin.getSymbol().toUpperCase(),split[1].toUpperCase(),btn2.getText().toString())
                         .enqueue(getCoinCurrentPrice(this));

                }
            }
            if (requestCode == REQUEST_CODE_EXCHANGE) {
                List categoryList = data.getStringArrayListExtra("Data");
                if(categoryList != null && !categoryList.isEmpty()) {
                    String name = categoryList.get(0).toString();
                    btn2.setText(name);

                    RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).getpair(coin.getSymbol(),name).enqueue(getCoinPairs(this));

                }
            }
        }

    }

    @Override
    public void onCurrentPriceLoaded(Response<ResponseBody> item) {
        super.onCurrentPriceLoaded(item);
        hideProgressDialog();
        try {
            String response = item.body().string();

            JSONObject resp= new JSONObject(response);

            String[] split = btn1.getText().toString().split("/");

            double val = resp.getDouble(split[1]);
            currentPrice.setText(val +"");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onExcahgnesLoaded(List<Exchange> item) {
        super.onExcahgnesLoaded(item);

        hideProgressDialog();
        for (Exchange exchange: item){
            exchanges.add(exchange.getExchange());
        }

        if(item !=  null && !item.isEmpty()) {
            btn2.setText(item.get(0).getExchange());

            showProgressDialog("Loading Pairs...");

            RetrofitUtil.cctAuthentication(CryptoApi.CCT_BASE_URL).getpair(coin.getSymbol(),item.get(0).getExchange()).enqueue(getCoinPairs(this));

        }
    }

    @Override
    public void onTradingPairLoaded(List<Pair> item) {
        super.onTradingPairLoaded(item);

        tradingPairs.clear();

        for (Pair tradingPair: item){
            tradingPairs.add(tradingPair.getPair());
        }

        hideProgressDialog();

        if(item !=  null && !item.isEmpty()) {
            btn1.setText(item.get(0).getPair());
            String[] split = btn1.getText().toString().split("/");

            showProgressDialog("Updating Current Price...");

            RetrofitUtil.cctAuthentication(CryptoApi.CURRENT_PRICE_BASE_URL).getCurrentPrice(coin.getSymbol().toUpperCase(),split[1].toUpperCase(),btn2.getText().toString())
                    .enqueue(getCoinCurrentPrice(this));
        }

    }

    @Override
    public void onBlockfolioUpdate(ResponseStatus item) {
        super.onBlockfolioUpdate(item);

        hideProgressDialog();
        if(item.getStatus()){
            Toast.makeText(this,"Blockfolio Updated!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Something is missing!",Toast.LENGTH_LONG).show();
        }
    }
}
