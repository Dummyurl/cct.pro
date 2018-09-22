package com.tregix.cryptocurrencytracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SAAD HASHMI on 7/18/2018.
 */

public class Exchange {
    @SerializedName("exchange")
    @Expose
   private String exchange;

    public Exchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchange() {
        return exchange;
    }
}
