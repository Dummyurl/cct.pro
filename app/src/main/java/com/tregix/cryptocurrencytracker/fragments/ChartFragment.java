package com.tregix.cryptocurrencytracker.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.robinhood.spark.SparkView;
import com.tregix.cryptocurrencytracker.adapter.ChartAdapter;
import com.tregix.cryptocurrencytracker.FetchGraphData;
import com.tregix.cryptocurrencytracker.Model.GraphData;
import com.tregix.cryptocurrencytracker.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChartFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public SparkView chart;
    TextView scrubInfoTextView;
    Button oneHour;
    Button oneMonth;
    Button oneWeek;
    Button oneYear;
    Button oneDay;


    private OnFragmentInteractionListener mListener;

    public ChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChartFragment newInstance(String param1, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chart, container, false);
        oneHour = view.findViewById(R.id.one_hour);
        oneMonth = view.findViewById(R.id.one_month);
        oneWeek = view.findViewById(R.id.one_week);
        oneYear = view.findViewById(R.id.one_year);
        oneDay = view.findViewById(R.id.one_day);
        chart = view.findViewById(R.id.sparkview);
        scrubInfoTextView = view.findViewById(R.id.scrub_info_textview);
        oneDay.setOnClickListener(this);
        oneMonth.setOnClickListener(this);
        oneWeek.setOnClickListener(this);
        oneHour.setOnClickListener(this);
        oneYear.setOnClickListener(this);

        chart.setScrubListener(new SparkView.OnScrubListener() {
            @Override
            public void onScrubbed(Object value) {
                if (value == null) {
                    scrubInfoTextView.setText(R.string.scrub_empty);
                } else {
                    scrubInfoTextView.setText(chart.getContext().getString(R.string.scrub_format, value));
                }
            }
        });
        oneHour.setSelected(true);
        fetchGraphData("histominute", "60");
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void unslectAll() {
        oneHour.setSelected(false);
        oneDay.setSelected(false);
        oneMonth.setSelected(false);
        oneWeek.setSelected(false);
        oneYear.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String method = "histominute";
        String limit = "60";
        unslectAll();
        switch (id) {
            case R.id.one_hour:
                oneHour.setSelected(true);
                break;
            case R.id.one_day:
                oneDay.setSelected(true);
                method = "histohour";
                limit = "24";
                break;
            case R.id.one_month:
                oneMonth.setSelected(true);
                method = "histoday";
                limit = "30";
                break;
            case R.id.one_week:
                oneWeek.setSelected(true);
                method = "histohour";
                limit = "168";
                break;
            case R.id.one_year:
                oneYear.setSelected(true);
                method = "histoday";
                limit = "365";
                break;
        }

        fetchGraphData(method, limit);
    }
    private void fetchGraphData(String apiMethod, String limit) {
        new FetchGraphData("https://min-api.cryptocompare.com/data/" + apiMethod +
                "?fsym=" + mParam1 + "&tsym=" + "USD" +
                "&limit=" + limit + "&aggregate=1&e=CCCAGG") {

            @Override
            protected void onPostExecute(List<GraphData> data) {
                try {
                    // if we didn't receive a response for the playlist titles, then there's nothing to update
                    if (data == null)
                        return;

                    chart.setAdapter(new ChartAdapter(data));
                    // chart.getAdapter().notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
