package com.tregix.cryptocurrencytracker.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.Model.cct.Categories;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.activities.WebviewActivity;
import com.tregix.cryptocurrencytracker.fragments.SignalsFragment;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignalsRecyclerViewAdapter extends RecyclerView.Adapter<SignalsRecyclerViewAdapter.ViewHolder> {
    private final List<Categories> mValues;
    private final SignalsFragment.OnListFragmentInteractionListener mListener;
    String result;

    public SignalsRecyclerViewAdapter(List<Categories> items, SignalsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public SignalsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new SignalsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SignalsRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Html.fromHtml(mValues.get(position).getTitle().getRendered()));
        holder.mContentView.setText(Html.fromHtml(mValues.get(position).getContent().getRendered()));
        String strDate= String.valueOf(Html.fromHtml(mValues.get(position).getDate()));
        splitDate(strDate);
        holder.date.setText(result);
        //Picasso.with(holder.newsIcon.getContext()).load(mValues.get(position).getFeaturedMedia()).into(holder.newsIcon);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.newsIcon.getContext(), WebviewActivity.class);
                i.putExtra("url",mValues.get(position).getLink());
                holder.newsIcon.getContext().startActivity(i);
            }
        });
        try {
            holder.publisher.setText(new URL(mValues.get(position).getLink()).getHost());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, holder.mItem.getLink());
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
        public Categories mItem;


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
    private void splitDate(String strDate) {
        Date date = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            date = dateFormat.parse(strDate);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat printFormat = new SimpleDateFormat("yyyy-MM-dd");
        result = printFormat.format(calendar.getTime());

    }
}
