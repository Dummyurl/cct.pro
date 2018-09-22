package com.tregix.cryptocurrencytracker.adapter;

import com.robinhood.spark.SparkAdapter;
import com.tregix.cryptocurrencytracker.Model.GraphData;

import java.util.List;

/**
 * Created by Confiz123 on 10/7/2017.
 */

public class ChartAdapter extends SparkAdapter {
    private List<GraphData> yData;

    public ChartAdapter(List<GraphData> yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.size();
    }

    @Override
    public Object getItem(int index) {
        return yData.get(index).getClose();
    }

    @Override
    public float getY(int index) {
        return Float.parseFloat(String.valueOf(yData.get(index).getClose()));
    }
}

