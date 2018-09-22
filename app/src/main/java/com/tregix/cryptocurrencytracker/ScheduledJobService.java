package com.tregix.cryptocurrencytracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tregix.cryptocurrencytracker.Model.Article;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.Model.Exchange;
import com.tregix.cryptocurrencytracker.Model.IcoItem;
import com.tregix.cryptocurrencytracker.Model.MarketCap;
import com.tregix.cryptocurrencytracker.Model.NewsItem;
import com.tregix.cryptocurrencytracker.Model.Pair;
import com.tregix.cryptocurrencytracker.Model.PortfolioItem;
import com.tregix.cryptocurrencytracker.Model.ResponseStatus;
import com.tregix.cryptocurrencytracker.Model.WatchlistItem;
import com.tregix.cryptocurrencytracker.Model.WatchlistUpdate;
import com.tregix.cryptocurrencytracker.Model.cct.Categories;
import com.tregix.cryptocurrencytracker.Model.singup.SignupData;
import com.tregix.cryptocurrencytracker.Model.user.User;
import com.tregix.cryptocurrencytracker.Retrofit.OnDataLoadListener;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil;
import com.tregix.cryptocurrencytracker.utils.Utils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.tregix.cryptocurrencytracker.Retrofit.RetrofitUtil.getDashboardData;

/**
 * Created by Confiz123 on 3/9/2018.
 */

public class ScheduledJobService extends BroadcastReceiver implements OnDataLoadListener{

    private Context cxt;

    @Override
    public void onReceive(Context context, Intent intent) {
        cxt = context;
        RetrofitUtil.createProviderAPI(CryptoApi.BASE_URL).loadDashBoard().enqueue(getDashboardData(this));
    }


    @Override
    public void onCoinLoaded(List<CoinListingContent.CoinItem> coin) {

    }

    @Override
    public void onNewsLoaded(NewsItem item) {

    }

    @Override
    public void onMarketCapLoaded(MarketCap item) {
        Utils.sendNotification(cxt,"Coin Market Cap Alert",
                "$" + Utils.getFormatedAmount(item.getTotalMarketCapUsd()));
    }

    @Override
    public void onGoogleNewsLoaded(List<Article> item) {

    }

    @Override
    public void onSignalLoaded(List<Categories> item) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoginAuthentication(Response<User> body) {

    }

    @Override
    public void onRegistration(Response<SignupData> registerDataResponse) {

    }

    @Override
    public void onNewsLoaded(List<Article> item) {

    }

    @Override
    public void onIcosLoaded(List<IcoItem> item) {

    }

    @Override
    public void onwatchlistLoaded(List<WatchlistItem> item) {

    }

    @Override
    public void onwatchlistUpdate(WatchlistUpdate update) {

    }

    @Override
    public void onPortfolioLoaded(List<PortfolioItem> item) {

    }

    @Override
    public void onExcahgnesLoaded(List<Exchange> item) {

    }

    @Override
    public void onTradingPairLoaded(List<Pair> item) {

    }

    @Override
    public void onCurrentPriceLoaded(Response<ResponseBody> item) {

    }

    @Override
    public void onBlockfolioUpdate(ResponseStatus item) {

    }
}
