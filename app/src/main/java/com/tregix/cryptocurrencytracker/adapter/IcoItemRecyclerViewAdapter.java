package com.tregix.cryptocurrencytracker.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tregix.cryptocurrencytracker.Model.IcoItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.activities.WebviewActivity;
import com.tregix.cryptocurrencytracker.fragments.CoinItemFragment.OnListFragmentInteractionListener;
import com.tregix.cryptocurrencytracker.fragments.IcoItemFragment;
import com.tregix.cryptocurrencytracker.fragments.TopNewsFragment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class IcoItemRecyclerViewAdapter extends RecyclerView.Adapter<IcoItemRecyclerViewAdapter.ViewHolder> {

    private final List<IcoItem> mValues;
    private final IcoItemFragment.OnListFragmentInteractionListener mListener;

    public IcoItemRecyclerViewAdapter(List<IcoItem> items, IcoItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public IcoItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new IcoItemRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IcoItemRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName().trim());
        holder.mContentView.setText(Html.fromHtml(mValues.get(position).getDescription()));
        holder.date.setText(mValues.get(position).getStartTime() +" - " + mValues.get(position).getEndTime());
        Picasso.get().load(holder.mItem.getImage()).into(holder.newsIcon);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.newsIcon.getContext(), WebviewActivity.class);
                i.putExtra("url",holder.mItem.getWebsite());
                holder.newsIcon.getContext().startActivity(i);
            }
        });
            holder.publisher.setText(mValues.get(position).getName());

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, holder.mItem.getWebsite());
                sendIntent.setType("text/plain");
                holder.share.getContext().startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView,publisher,date;
        public final ImageView newsIcon,share;
        public IcoItem mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            newsIcon = view.findViewById(R.id.news_icon);
            publisher = view.findViewById(R.id.souce_name);
            date = view.findViewById(R.id.date);
            share = view.findViewById(R.id.share);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
