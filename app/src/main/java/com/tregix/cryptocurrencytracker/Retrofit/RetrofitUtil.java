package com.tregix.cryptocurrencytracker.Retrofit;

import android.text.Html;
import android.util.Log;

import com.tregix.cryptocurrencytracker.BuildConfig;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.toptas.rssconverter.RssConverterFactory;
import me.toptas.rssconverter.RssFeed;
import me.toptas.rssconverter.RssItem;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Confiz123 on 11/29/2017.
 */

public class RetrofitUtil {

    public static CryptoApi createProviderAPI(String url) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("auth", "mobileappauth"); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(url).client(client).
                        addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(CryptoApi.class);
    }

    public static CryptoApi createGoogleNewsApi(String url) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(url).client(client).
                        addConverterFactory(RssConverterFactory.create()).build();

        return retrofit.create(CryptoApi.class);
    }

    public static CryptoApi cctAuthentication(String url) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("auth", "mobileappauth"); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit =
                new Retrofit.Builder().baseUrl(url).client(client).
                        addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(CryptoApi.class);
    }

    public static Callback<List<CoinListingContent.CoinItem>> getCoinListing(final OnDataLoadListener listener) {

        return new Callback<List<CoinListingContent.CoinItem>>() {
            @Override
            public void onResponse(Call<List<CoinListingContent.CoinItem>> call, Response<List<CoinListingContent.CoinItem>> response) {
                if (response.isSuccessful()) {
                    List<CoinListingContent.CoinItem> data = new ArrayList<>();
                    if (response.body() != null) {
                        CoinListingContent.addItem(response.body());
                        data.addAll(response.body());
                    }
                    listener.onCoinLoaded(data);
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CoinListingContent.CoinItem>> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }
    public static Callback<NewsItem> getTopNews(final OnDataLoadListener listener) {

        return new Callback<NewsItem>() {
            @Override
            public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {
                if (response.isSuccessful()) {
                    NewsItem.addItem(response.body().getArticles());
                    listener.onNewsLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NewsItem> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<MarketCap> getDashboardData(final OnDataLoadListener listener) {

        return new Callback<MarketCap>() {
            @Override
            public void onResponse(Call<MarketCap> call, Response<MarketCap> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                    listener.onMarketCapLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MarketCap> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static void getAllNews(String key,final OnDataLoadListener listener) {
        CryptoApi service = RetrofitUtil.createGoogleNewsApi(CryptoApi.GOOGLE_NEWS_BASE_URL);
        service.getRss("https://news.google.com/news/rss/search/section/q/" + key)
                .enqueue(new Callback<RssFeed>() {
                    @Override
                    public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                        List<Article> list = new ArrayList<>();
                        for(RssItem item: response.body().getItems()){
                            Article article = new Article();
                            article.setTitle(item.getTitle());
                            article.setDescription(Html.fromHtml(item.getDescription()).toString());
                            article.setUrlToImage(item.getImage());
                            article.setUrl(item.getLink());
                            article.setPublishedAt(item.getPublishDate());
                            list.add(article);
                        }
                        listener.onGoogleNewsLoaded(list);
                    }

                    @Override
                    public void onFailure(Call<RssFeed> call, Throwable t) {
                        Log.d("error",t.getLocalizedMessage());
                        listener.onError();
                    }
                });
    }

    public static Callback<List<Categories>> getSignals(final OnDataLoadListener listener) {

        return new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)

                        listener.onSignalLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<User> loginAuth(final OnDataLoadListener listener)
    {
        return new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    listener.onLoginAuthentication(response);
                }
                else
                {listener.onError();}
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }
    public static Callback<SignupData> registerDataCallback(final OnDataLoadListener listener)
    {
        return new Callback<SignupData>() {
            @Override
            public void onResponse(Call<SignupData> call, Response<SignupData> response) {
                if(response.isSuccessful())
                {
                    listener.onRegistration(response);
                } else
                {listener.onError();}
            }

            @Override
            public void onFailure(Call<SignupData> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<List<Article>> getAllNews(final OnDataLoadListener listener) {
        return new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful())
                {
                    listener.onNewsLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {

            }
        };
    }

    public static Callback<List<IcoItem>> getIcos(final OnDataLoadListener listener) {
        return new Callback<List<IcoItem>>() {
            @Override
            public void onResponse(Call<List<IcoItem>> call, Response<List<IcoItem>> response) {
                if(response.isSuccessful())
                {
                    listener.onIcosLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<IcoItem>> call, Throwable t) {

            }
        };
    }

    public static Callback<List<WatchlistItem>> getUserWatchlist(final OnDataLoadListener listener) {

        return new Callback<List<WatchlistItem>>() {
            @Override
            public void onResponse(Call<List<WatchlistItem>> call, Response<List<WatchlistItem>> response) {
                if (response.isSuccessful()) {
                    listener.onwatchlistLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<WatchlistItem>> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<WatchlistUpdate> saveUserWatchlist(final OnDataLoadListener listener) {

        return new Callback<WatchlistUpdate>() {
            @Override
            public void onResponse(Call<WatchlistUpdate> call, Response<WatchlistUpdate> response) {
                if (response.isSuccessful()) {
                    listener.onwatchlistUpdate(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WatchlistUpdate> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<List<PortfolioItem>> getUserPortfolio(final OnDataLoadListener listener) {

        return new Callback<List<PortfolioItem>>() {
            @Override
            public void onResponse(Call<List<PortfolioItem>> call, Response<List<PortfolioItem>> response) {
                if (response.isSuccessful()) {
                    listener.onPortfolioLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<PortfolioItem>> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<List<Exchange>> getExchanges(final OnDataLoadListener listener) {

        return new Callback<List<Exchange>>() {
            @Override
            public void onResponse(Call<List<Exchange>> call, Response<List<Exchange>> response) {
                if (response.isSuccessful()) {
                    listener.onExcahgnesLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Exchange>> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }


    public static Callback<List<Pair>> getCoinPairs(final OnDataLoadListener listener) {

        return new Callback<List<Pair>>() {
            @Override
            public void onResponse(Call<List<Pair>> call, Response<List<Pair>> response) {
                if (response.isSuccessful()) {
                    listener.onTradingPairLoaded(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Pair>> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<ResponseBody> getCoinCurrentPrice(final OnDataLoadListener listener) {

        return new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    listener.onCurrentPriceLoaded(response);
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }

    public static Callback<ResponseStatus> updateUserBlockfolio(final OnDataLoadListener listener) {

        return new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.isSuccessful()) {
                    listener.onBlockfolioUpdate(response.body());
                } else {
                    listener.onError();
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                t.printStackTrace();
                listener.onError();
            }
        };
    }
}
