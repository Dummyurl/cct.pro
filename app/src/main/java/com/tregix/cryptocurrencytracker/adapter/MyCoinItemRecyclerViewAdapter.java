package com.tregix.cryptocurrencytracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;
import com.robinhood.spark.SparkView;
import com.squareup.picasso.Picasso;
import com.tregix.cryptocurrencytracker.FetchGraphData;
import com.tregix.cryptocurrencytracker.Model.GraphData;
import com.tregix.cryptocurrencytracker.Retrofit.CryptoApi;
import com.tregix.cryptocurrencytracker.activities.BaseActivity;
import com.tregix.cryptocurrencytracker.activities.CoinDetailActivity;
import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.utils.Utils;
import com.tregix.cryptocurrencytracker.fragments.CoinItemFragment.OnListFragmentInteractionListener;

import java.util.HashSet;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCoinItemRecyclerViewAdapter extends RecyclerView.Adapter<MyCoinItemRecyclerViewAdapter.ViewHolder> {

    private final List<CoinListingContent.CoinItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private int type;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    public MyCoinItemRecyclerViewAdapter(List<CoinListingContent.CoinItem> items, OnListFragmentInteractionListener listener,
                                         int selectedTab) {
        mValues = items;
        mListener = listener;
        type = selectedTab;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cell, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_coinitem, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return BaseActivity.isCardView;
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder.chart != null) {
            holder.chart.setAdapter(null);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        if (holder.getItemViewType() == 1) {
            final FoldingCell cell = (FoldingCell) holder.itemView;


            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }

            holder.mIdView.setText(mValues.get(position).getName());
            holder.contentName.setText(mValues.get(position).getName());

            holder.symbol.setText(holder.mItem.getSymbol());
            holder.contentSymbol.setText(holder.mItem.getSymbol());

            holder.btcPrice.setText("BTC " + holder.mItem.getPriceBtc());
            holder.contentBtc.setText("BTC " + holder.mItem.getPriceBtc());

            if ((type != 2) && mValues.get(position).getPriceUsd() != null) {
                holder.mContentView.setText("$" + Utils.getFormatedAmount(Double.parseDouble(mValues.get(position).getPriceUsd())));
                holder.contentPrice.setText("$" + Utils.getFormatedAmount(Double.parseDouble(mValues.get(position).getPriceUsd())));

            } else if (mValues.get(position).getPriceBtc() != null) {
                holder.mContentView.setText(mValues.get(position).getPriceBtc());
            }

            int red = holder.changehour.getContext().getResources().getColor(R.color.colorRed);
            int green = holder.changehour.getContext().getResources().getColor(R.color.colorGreen);

            if (mValues.get(position).getPercentChange1h() != null && Float.parseFloat(mValues.get(position).getPercentChange1h()) > 0) {
                // holder.basicView.setBackgroundColor(green);
                holder.changehour.setTextColor(green);
                //  holder.contentChangehour.setTextColor(green);
                holder.contentBtc.setBackgroundColor(green);
                holder.header.setBackgroundColor(green);

            } else {
                //  holder.basicView.setBackgroundColor(red);
                holder.changehour.setTextColor(red);
                // holder.contentChangehour.setTextColor(red);
                holder.contentBtc.setBackgroundColor(red);
                holder.header.setBackgroundColor(red);

            }
            if (mValues.get(position).getPercentChange24h() != null && Float.parseFloat(mValues.get(position).getPercentChange24h()) > 0) {
                holder.changeday.setTextColor(holder.changeday.getContext().getResources().getColor(R.color.colorGreen));
                //  holder.contentChangeday.setTextColor(green);
            } else {
                holder.changeday.setTextColor(holder.changeday.getContext().getResources().getColor(R.color.colorRed));
                //  holder.contentChangeday.setTextColor(red);
            }
            if (mValues.get(position).getPercentChange7d() != null && Float.parseFloat(mValues.get(position).getPercentChange7d()) > 0) {
                holder.change7d.setTextColor(holder.change7d.getContext().getResources().getColor(R.color.colorGreen));
                //  holder.contentChange7d.setTextColor(green);
            } else {
                //holder.contentChange7d.setTextColor(red);
                holder.change7d.setTextColor(holder.change7d.getContext().getResources().getColor(R.color.colorRed));
            }

            if (mValues.get(position).getPercentChange24h() != null) {
                holder.changeday.setText(mValues.get(position).getPercentChange24h() + "%");
                holder.contentChangeday.setText(mValues.get(position).getPercentChange24h() + "%");
            } else {
                holder.changeday.setText("?");
                holder.contentChangeday.setText("?");
            }

            if (mValues.get(position).getPercentChange1h() != null) {
                holder.changehour.setText(mValues.get(position).getPercentChange1h() + "%");
                holder.contentChangehour.setText(mValues.get(position).getPercentChange1h() + "%");
            } else {
                holder.contentChangehour.setText("?");
                holder.changehour.setText("?");
            }

            if (mValues.get(position).getPercentChange7d() != null) {
                holder.change7d.setText(mValues.get(position).getPercentChange7d() + "%");
                holder.contentChange7d.setText(mValues.get(position).getPercentChange7d() + "%");
            } else {
                holder.change7d.setText("?");
                holder.contentChange7d.setText("?");

            }

            if (mValues.get(position).getMarketCapUsd() != null) {
                holder.marketCap.setText("$" + Utils.truncateNumber(Double.parseDouble(mValues.get(position).getMarketCapUsd())));
            } else {
                holder.marketCap.setText("?");
            }

            if (mValues.get(position).get24hVolumeUsd() != null) {
                holder.volume.setText("$" + Utils.truncateNumber(Double.parseDouble(mValues.get(position).get24hVolumeUsd())));
            } else {
                holder.volume.setText("?");
            }

            if (mValues.get(position).getAvailableSupply() != null) {
                holder.contentSupply.setText(Utils.truncateNumber(Double.parseDouble(mValues.get(position).getAvailableSupply())));
            } else {
                holder.contentSupply.setText("?");
            }
            if (mValues.get(position).getMaxSupply() != null) {
                holder.contentTotalSupply.setText(Utils.truncateNumber(Double.parseDouble(mValues.get(position).getMaxSupply())));
            } else {
                holder.contentTotalSupply.setText("?");
            }
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cell.toggle(false);
                    registerToggle(position);
                }
            });
            holder.detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.mView.getContext(), CoinDetailActivity.class);
                    intent.putExtra("coin", holder.mItem);
                    holder.mView.getContext().startActivity(intent);
                }
            });
            Picasso.get()
                    .load(CryptoApi.ICONS_LINK + mValues.get(position).getSymbol().toLowerCase() + "_.png")
                    .into(holder.icon);
            Picasso.get()
                    .load(CryptoApi.ICONS_LINK + mValues.get(position).getSymbol().toLowerCase() + "_.png")
                    .into(holder.contentIcon);

            holder.fetchGraphData("histohour", "24");
            holder.rank.setText(holder.mItem.getRank() + "");
            holder.contentRank.setText(holder.mItem.getRank() + "");

            if (Utils.isFavorite(holder.mView.getContext(), mValues.get(position).getSymbol())) {
                holder.favrt.setBackground(holder.mView.getContext().getResources().getDrawable(R.drawable.ic_star));
            } else {
                holder.favrt.setBackground(holder.mView.getContext().getResources().getDrawable(R.drawable.ic_star_empty));
            }
            holder.favrt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.updateFavorite(holder.mView.getContext(), holder.mItem.getSymbol());
                    if (Utils.isFavorite(holder.mView.getContext(), mValues.get(position).getSymbol())) {
                        holder.favrt.setBackground(holder.mView.getContext().getResources().getDrawable(R.drawable.ic_star));
                    } else {
                        holder.favrt.setBackground(holder.mView.getContext().getResources().getDrawable(R.drawable.ic_star_empty));
                    }
                }
            });
        } else {
            holder.symbol.setText(mValues.get(position).getName());
            if ( (type != 2) && mValues.get(position).getPriceUsd() != null) {
                holder.mContentView.setText("$" + Utils.getFormatedAmount(Double.parseDouble(mValues.get(position).getPriceUsd())));
            }else if(mValues.get(position).getPriceBtc() != null){
                holder.mContentView.setText( mValues.get(position).getPriceBtc());
            }



            if (mValues.get(position).getPercentChange24h() != null && Float.parseFloat(mValues.get(position).getPercentChange24h()) > 0) {
                holder.changeday.setBackgroundColor(holder.changeday.getContext().getResources().getColor(R.color.colorGreen));
                holder.changeday.setTextColor(holder.changeday.getContext().getResources().getColor(android.R.color.white));
            } else {
                holder.changeday.setBackgroundColor(holder.changeday.getContext().getResources().getColor(R.color.colorRed));
                holder.changeday.setTextColor(holder.changeday.getContext().getResources().getColor(android.R.color.black));

            }
            if (mValues.get(position).getPercentChange1h() != null && Float.parseFloat(mValues.get(position).getPercentChange1h()) > 0) {
                holder.changehour.setBackgroundColor(holder.changehour.getContext().getResources().getColor(R.color.colorGreen));
                holder.changehour.setTextColor(holder.changeday.getContext().getResources().getColor(android.R.color.white));
            } else {
                holder.changehour.setBackgroundColor(holder.changehour.getContext().getResources().getColor(R.color.colorRed));
                holder.changehour.setTextColor(holder.changeday.getContext().getResources().getColor(android.R.color.black));

            }

            if (mValues.get(position).getPercentChange24h() != null)
                holder.changeday.setText(mValues.get(position).getPercentChange24h() + "%");
            else{
                holder.changeday.setText("?");
            }

            if (mValues.get(position).getPercentChange1h() != null)
                holder.changehour.setText(mValues.get(position).getPercentChange1h() + "%");
            else{
                holder.changehour.setText("?");
            }

            if (mValues.get(position).getMarketCapUsd() != null) {
                holder.marketCap.setText("$" + Utils.truncateNumber(Double.parseDouble(mValues.get(position).getMarketCapUsd())));
            }else{
                holder.marketCap.setText("?");
            }

            if (mValues.get(position).get24hVolumeUsd() != null) {
                holder.volume.setText("$" + Utils.getFormatedAmount(Double.parseDouble(mValues.get(position).get24hVolumeUsd())));
            }else{
                holder.volume.setText("?");
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                    //holder.toggleCardViewnHeight(getScreenHeight(holder.mView.getContext()));
                    Intent intent = new Intent(holder.mView.getContext(), CoinDetailActivity.class);
                    intent.putExtra("coin",holder.mItem);
                    holder.mView.getContext().startActivity(intent);
                }
            });
        }
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        public final View mView;
        public final TextView mIdView, change7d, btcPrice, symbol, rank;
        public final TextView mContentView, changehour, changeday, marketCap, volume;

        public final TextView contentPrice, contentChange7d, contentSymbol, contentChangehour, contentChangeday, contentName;
        public final TextView contentSupply, contentTotalSupply, contentBtc, contentRank;
        public View basicView, graphView;
        public CoinListingContent.CoinItem mItem;
        public int minHeight;
        public ImageView icon, contentIcon, favrt;
        public SparkView chart, contentChart;
        public TextView detail;
        public RelativeLayout header;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.title_name);
            basicView = view.findViewById(R.id.basic_view);
            mContentView = (TextView) view.findViewById(R.id.title_price);
            changehour = view.findViewById(R.id.title_1h_chg);
            changeday = view.findViewById(R.id.title_24h_chg);
            change7d = view.findViewById(R.id.title_7d_chg);
            btcPrice = view.findViewById(R.id.title_btc_price);
            symbol = view.findViewById(R.id.title_symbol);
            marketCap = view.findViewById(R.id.market_cap);
            volume = view.findViewById(R.id.vol);
            icon = view.findViewById(R.id.title_coin_logo);
            chart = view.findViewById(R.id.sparkview);
            rank = view.findViewById(R.id.title_rank);
            favrt = view.findViewById(R.id.content_favt);
            contentPrice = view.findViewById(R.id.content_price);
            contentChangehour = view.findViewById(R.id.content_1h);
            contentChangeday = view.findViewById(R.id.content_24h);
            contentChange7d = view.findViewById(R.id.content_7d);
            contentSymbol = view.findViewById(R.id.content_symbol);
            contentSupply = view.findViewById(R.id.content_supply);
            contentTotalSupply = view.findViewById(R.id.content_total_supply);
            contentIcon = view.findViewById(R.id.content_avatar);
            contentName = view.findViewById(R.id.content_name_view);
            contentChart = view.findViewById(R.id.content_spark);
            contentBtc = view.findViewById(R.id.head_image);
            contentRank = view.findViewById(R.id.content_rank);

            detail = view.findViewById(R.id.content_open_detail);
            header = view.findViewById(R.id.content_header);
        }

        public void fetchGraphData(String apiMethod, String limit) {
            new FetchGraphData("https://min-api.cryptocompare.com/data/" + apiMethod +
                    "?fsym=" + mItem.getSymbol() + "&tsym=" + "USD" +
                    "&limit=" + limit + "&aggregate=1&e=CCCAGG") {

                @Override
                protected void onPostExecute(List<GraphData> data) {
                    try {
                        // if we didn't receive a response for the playlist titles, then there's nothing to update
                        if (data == null)
                            return;

                        chart.setAdapter(new ChartAdapter(data));
                        contentChart.setAdapter(new ChartAdapter(data));
                        // chart.getAdapter().notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }


    }


}
