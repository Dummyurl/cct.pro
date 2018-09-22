package com.tregix.cryptocurrencytracker.Retrofit;

import com.tregix.cryptocurrencytracker.Model.Article;
import com.tregix.cryptocurrencytracker.Model.BlockfolioUpdateItem;
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
import com.tregix.cryptocurrencytracker.Model.cct.LoginData;
import com.tregix.cryptocurrencytracker.Model.cct.SignupDataParams;
import com.tregix.cryptocurrencytracker.Model.user.User;

import java.util.List;

import me.toptas.rssconverter.RssFeed;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Confiz123 on 11/21/2017.
 */

public interface CryptoApi {

    String BASE_URL = "https://api.coinmarketcap.com";
    String GOOGLE_NEWS_BASE_URL = "https://news.google.com";

    String CCT_BASE_URL =  "https://cryptocurrencytracking.pro/";

    String ICONS_LINK = CCT_BASE_URL + "/public/images/coins_icons/thumbs/";
    String CURRENT_PRICE_BASE_URL = "https://min-api.cryptocompare.com/";

    @GET("/v1/ticker/?limit=0")
    Call<List<CoinListingContent.CoinItem>> loadCoinsListing(@Query("CONVERT") String convert);

    @GET("/v1/global")
    Call<MarketCap> loadDashBoard();

    @GET("/v2/top-headlines?sources=crypto-coins-news&apiKey=73856379751b4986b00d72ef47bbbea7")
    Call<NewsItem> loadTopNews();

    /**
     * No baseUrl defined. Each RSS Feed will be consumed by it's Url
     *
     * @param url RSS Feed Url
     * @return Retrofit Call
     */
    @GET
    Call<RssFeed> getRss(@Url String url);

    @GET("/wp-json/wp/v2/posts?")
    Call<List<Categories>> loadSignals(@Query("categories") String category);

    @GET("api/v1/icos/{type}?auth=mobileappauth")
    Call<List<IcoItem>> loadIcos(@Path("type") int type);

    @GET("api/v1/news/50?auth=mobileappauth")
    Call<List<Article>> loadNews();

    @GET("/api/v1/search-news/{coin}?auth=mobileappauth")
    Call<List<Article>> getCoinNews(@Path("coin") String coin );

    @GET("api/v1/watchlist/{user_id}?auth=mobileappauth")
    Call<List<WatchlistItem>> getUserWatchlist(@Path("user_id") int userId);

    @POST("api/v1/user/do/login?auth=mobileappauth")
    Call<User> login(@Body LoginData loginData);

    @POST("api/v1/user/do/register?auth=mobileappauth")
    Call<com.tregix.cryptocurrencytracker.Model.singup.SignupData> registration(@Body SignupDataParams registerData);

    @POST("api/v1/save-watchlist/{user_id}/{coin}?auth=mobileappauth")
    Call<WatchlistUpdate> saveWatchlist(@Path("user_id") int userId, @Path("coin") String coin);

    @GET("api/v1/blockfolio/get/{user_id}?auth=mobileappauth")
    Call<List<PortfolioItem>> getUserBlockfolio(@Path("user_id") int userId);

    @GET("api/v1/blockfolio/exchanges/{coin}?auth=mobileappauth")
    Call<List<Exchange>> getExchange(@Path("coin") String coin );

    @GET("api/v1/blockfolio/pairs/{coin}/{exchange}?auth=mobileappauth")
    Call<List<Pair>> getpair(@Path("coin") String coin , @Path("exchange") String exchange);

    @GET("/data/price")
    Call<ResponseBody> getCurrentPrice(@Query("fsym") String userid,
                                       @Query("tsyms") String categoryId,
                                       @Query("e") String keyword);

    @POST("api/v1/blockfolio/save/{user_id}?auth=mobileappauth")
    Call<ResponseStatus> updateUserPortfolio(@Path("user_id") int userId, @Body BlockfolioUpdateItem coin);
}
