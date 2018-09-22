package com.tregix.cryptocurrencytracker.Retrofit;

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

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Gohar Ali on 2/21/2018.
 */

public interface OnDataLoadListener {

    void onCoinLoaded(List<CoinListingContent.CoinItem> coin);
    void onNewsLoaded(NewsItem item);
    void onMarketCapLoaded(MarketCap item);
    void onGoogleNewsLoaded(List<Article> item);
    void onSignalLoaded(List<Categories> item);
    void onError();

    void onLoginAuthentication(Response<User> body);
    void onRegistration(Response<SignupData> registerDataResponse);
    void onNewsLoaded(List<Article> item);
    void onIcosLoaded(List<IcoItem> item);
    void onwatchlistLoaded(List<WatchlistItem> item);
    void onwatchlistUpdate(WatchlistUpdate update);
    void onPortfolioLoaded(List<PortfolioItem> item);
    void onExcahgnesLoaded(List<Exchange> item);
    void onTradingPairLoaded(List<Pair> item);
    void onCurrentPriceLoaded(Response<ResponseBody> item);
    void onBlockfolioUpdate(ResponseStatus item);


}