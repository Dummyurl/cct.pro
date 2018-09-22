package com.tregix.cryptocurrencytracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gohar Ali on 2/22/2018.
 */

public class MarketCap {

    @SerializedName("total_market_cap_usd")
    @Expose
    private Double totalMarketCapUsd;
    @SerializedName("total_24h_volume_usd")
    @Expose
    private Double total24hVolumeUsd;
    @SerializedName("bitcoin_percentage_of_market_cap")
    @Expose
    private Double bitcoinPercentageOfMarketCap;
    @SerializedName("active_currencies")
    @Expose
    private Integer activeCurrencies;
    @SerializedName("active_assets")
    @Expose
    private Integer activeAssets;
    @SerializedName("active_markets")
    @Expose
    private Integer activeMarkets;
    @SerializedName("last_updated")
    @Expose
    private Integer lastUpdated;

    public Double getTotalMarketCapUsd() {
        return totalMarketCapUsd;
    }

    public void setTotalMarketCapUsd(Double totalMarketCapUsd) {
        this.totalMarketCapUsd = totalMarketCapUsd;
    }

    public Double getTotal24hVolumeUsd() {
        return total24hVolumeUsd;
    }

    public void setTotal24hVolumeUsd(Double total24hVolumeUsd) {
        this.total24hVolumeUsd = total24hVolumeUsd;
    }

    public Double getBitcoinPercentageOfMarketCap() {
        return bitcoinPercentageOfMarketCap;
    }

    public void setBitcoinPercentageOfMarketCap(Double bitcoinPercentageOfMarketCap) {
        this.bitcoinPercentageOfMarketCap = bitcoinPercentageOfMarketCap;
    }

    public Integer getActiveCurrencies() {
        return activeCurrencies;
    }

    public void setActiveCurrencies(Integer activeCurrencies) {
        this.activeCurrencies = activeCurrencies;
    }

    public Integer getActiveAssets() {
        return activeAssets;
    }

    public void setActiveAssets(Integer activeAssets) {
        this.activeAssets = activeAssets;
    }

    public Integer getActiveMarkets() {
        return activeMarkets;
    }

    public void setActiveMarkets(Integer activeMarkets) {
        this.activeMarkets = activeMarkets;
    }

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
