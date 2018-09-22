package com.tregix.cryptocurrencytracker;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tregix.cryptocurrencytracker.Model.GraphData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Confiz123 on 10/7/2017.
 */

public class FetchGraphData extends AsyncTask<Void, Void, List<GraphData>> {

    private String dataApi;

    public FetchGraphData(String api) {
        dataApi = api;
    }

    @Override
    protected List<GraphData> doInBackground(Void... params) {


        try {

            URL url = new URL(dataApi);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();

                JSONObject obj = new JSONObject(stringBuilder.toString());

                Type listType = new TypeToken<List<GraphData>>() {
                }.getType();

                List<GraphData> yourList = new Gson().fromJson(obj.getJSONArray("Data").toString(), listType);

                return yourList;
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }
}