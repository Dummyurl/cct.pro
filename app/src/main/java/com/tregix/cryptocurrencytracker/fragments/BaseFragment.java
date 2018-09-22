package com.tregix.cryptocurrencytracker.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.OnDataLoadListener;
import com.tregix.cryptocurrencytracker.activities.LoginActivity;
import com.tregix.cryptocurrencytracker.utils.Utils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment implements OnDataLoadListener,View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(getActivity() != null) {
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCoinLoaded(List<CoinListingContent.CoinItem> coin) {

    }

    @Override
    public void onNewsLoaded(NewsItem item) {

    }

    @Override
    public void onMarketCapLoaded(MarketCap item) {

    }

    @Override
    public void onGoogleNewsLoaded(List<Article> item) {

    }

    @Override
    public void onSignalLoaded(List<Categories> item) {

    }

    @Override
    public void onError() {
        Utils.showError(getActivity());
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

    @Override
    public void onClick(View view) {

    }

    protected void showDialogLogin(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage(msg)
                .setPositiveButton(R.string.title_login, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        dialog.dismiss();

                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

}
