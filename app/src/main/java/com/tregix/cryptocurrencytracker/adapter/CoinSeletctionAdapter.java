package com.tregix.cryptocurrencytracker.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.Model.CoinListingContent;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.activities.AddTransactionActivity;
import com.tregix.cryptocurrencytracker.activities.CoinDetailActivity;
import com.tregix.cryptocurrencytracker.fragments.CoinItemFragment.OnListFragmentInteractionListener;

import java.util.HashSet;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CoinSeletctionAdapter extends RecyclerView.Adapter<CoinSeletctionAdapter.ViewHolder> {

    private final List<CoinListingContent.CoinItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private int type;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    public CoinSeletctionAdapter(List<CoinListingContent.CoinItem> items, OnListFragmentInteractionListener listener,
                                 int selectedTab) {
        mValues = items;
        mListener = listener;
        type = selectedTab;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_select_coin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

            holder.name.setText(mValues.get(position).getName() + " : " + mValues.get(position).getSymbol());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(holder.mView.getContext(), AddTransactionActivity.class);
                    intent.putExtra("coin", holder.mItem);
                    holder.mView.getContext().startActivity(intent);
                }
            });
    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        public final View mView;
        public final TextView name;
        public CoinListingContent.CoinItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.coin_name);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }


    }


}
