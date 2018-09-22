package com.tregix.cryptocurrencytracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentPrice {

    @SerializedName("USD")
    @Expose
    private String usd;

    public CurrentPrice(String pair) {
        this.usd = pair;
    }

    public String getPrice() {
        return usd;
    }

}
