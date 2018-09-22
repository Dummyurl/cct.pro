package com.tregix.cryptocurrencytracker.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tregix.cryptocurrencytracker.Model.PortfolioItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.activities.CoinDetailActivity;
import com.tregix.cryptocurrencytracker.fragments.CoinItemFragment.OnListFragmentInteractionListener;
import com.tregix.cryptocurrencytracker.utils.Utils;

import java.util.List;

import okhttp3.internal.Util;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPortfolioRecyclerViewAdapter extends RecyclerView.Adapter<MyPortfolioRecyclerViewAdapter.ViewHolder> {

    private final List<PortfolioItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPortfolioRecyclerViewAdapter(List<PortfolioItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MyPortfolioRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.blockfolio_item, parent, false);
        return new MyPortfolioRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        String[] split = mValues.get(position).getPair().split("/");

        holder.symbol.setText(mValues.get(position).getCoin());

        float amount = (Float.parseFloat(mValues.get(position).getTotalQuantity()) * Float.parseFloat(mValues.get(position).getPrice())) ;

        holder.totalAmount.setText(Utils.getFormatedAmount(amount) + " " + split[1]);

        holder.quantity.setText(Utils.getFormatedAmount(Float.parseFloat(mValues.get(position).getTotalQuantity())));

        holder.price.setText(Utils.getFormatedAmount(Float.parseFloat(mValues.get(position).getPrice())) + " " + split[1] );


        float change = Float.parseFloat(mValues.get(position).getProfitLoss());

        if(change<0){
            holder.profitLoss.setText( Utils.getFormatedAmount(Float.parseFloat(mValues.get(position).getProfitLoss())));
            holder.profitLoss.setTextColor(holder.profitLoss.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }else{
            holder.profitLoss.setText( "+" + Utils.getFormatedAmount(Float.parseFloat(mValues.get(position).getProfitLoss())));
            holder.profitLoss.setTextColor(holder.profitLoss.getContext().getResources().getColor(android.R.color.holo_green_light));
        }

        holder.change.setText(Utils.getFormatedAmount(Float.parseFloat(mValues.get(position).getCurrentPrice())) + " " + split[1]);

        Picasso.get()
                .load(CryptoApi.ICONS_LINK + mValues.get(position).getCoin().toLowerCase() + "_.png")
                .into(holder.icon);

     /*   holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.mView.getContext(), CoinDetailActivity.class);
                    intent.putExtra("coin",holder.mItem);
                    holder.mView.getContext().startActivity(intent);
                }
            });*/
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public final View mView;
        public final TextView  totalAmount, quantity, symbol, price,change,profitLoss;
        public PortfolioItem mItem;
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            totalAmount = (TextView) view.findViewById(R.id.title_total_amount);
            quantity = view.findViewById(R.id.quantity);
            price = (TextView) view.findViewById(R.id.title_price);
            symbol = view.findViewById(R.id.title_symbol);
            icon = view.findViewById(R.id.icon);
            change = view.findViewById(R.id.change);
            profitLoss = view.findViewById(R.id.title_profit_loss);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + symbol.getText() + "'";
        }


    }


}
