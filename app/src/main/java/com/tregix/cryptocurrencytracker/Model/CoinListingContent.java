package com.tregix.cryptocurrencytracker.Model;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tregix.cryptocurrencytracker.utils.SharedPreferenceUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CoinListingContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<CoinItem> ITEMS = new ArrayList<CoinItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, CoinItem> ITEM_MAP = new HashMap<String, CoinItem>();

    private static final int COUNT = 25;


    public static void addItem(List<CoinItem> item) {
        if (item != null) {
            if (ITEMS.size() > 0) {
                ITEMS.clear();
            }
            ITEMS.addAll(item);
        }
    }

    public static List<CoinItem> sort24hAscending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {
            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getPercentChange24h() != null && o2.getPercentChange24h() != null) {
                        double order1 = Double.parseDouble(o1.getPercentChange24h());
                        double order2 = Double.parseDouble(o2.getPercentChange24h());
                        return compareItemsAscending(order1, order2);
                    } else {
                        if (o1.getPercentChange24h() != null) {
                            return 1;
                        } else if (o2.getPercentChange24h() != null) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> sort24hDecending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {

            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getPercentChange24h() != null && o2.getPercentChange24h() != null) {
                        double order1 = Double.parseDouble(o1.getPercentChange24h());
                        double order2 = Double.parseDouble(o2.getPercentChange24h());
                        return compareItemsDscending(order1, order2);
                    } else {
                        if (o1.getPercentChange24h() != null) {
                            return -1;
                        } else if (o2.getPercentChange24h() != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> sort7dDecending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {

            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getPercentChange7d() != null && o2.getPercentChange7d() != null) {
                        double order1 = Double.parseDouble(o1.getPercentChange7d());
                        double order2 = Double.parseDouble(o2.getPercentChange7d());
                        return compareItemsDscending(order1, order2);
                    } else {
                        if (o1.getPercentChange7d() != null) {
                            return -1;
                        } else if (o2.getPercentChange7d() != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }

    public static List<CoinItem> sort7dAscending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {
            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getPercentChange7d() != null && o2.getPercentChange7d() != null) {
                        double order1 = Double.parseDouble(o1.getPercentChange7d());
                        double order2 = Double.parseDouble(o2.getPercentChange7d());
                        return compareItemsAscending(order1, order2);
                    } else {
                        if (o1.getPercentChange7d() != null) {
                            return 1;
                        } else if (o2.getPercentChange7d() != null) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> sortVol24hDecending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {

            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.get24hVolumeUsd() != null && o2.get24hVolumeUsd() != null) {
                        double order1 = Double.parseDouble(o1.get24hVolumeUsd());
                        double order2 = Double.parseDouble(o2.get24hVolumeUsd());
                        return compareItemsDscending(order1, order2);
                    } else {
                        if (o1.get24hVolumeUsd() != null) {
                            return -1;
                        } else if (o2.get24hVolumeUsd() != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> sortMCDecending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {

            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getMarketCapUsd() != null && o2.getMarketCapUsd() != null) {
                        double order1 = Double.parseDouble(o1.getMarketCapUsd());
                        double order2 = Double.parseDouble(o2.getMarketCapUsd());
                        return compareItemsDscending(order1, order2);
                    } else {
                        if (o1.getMarketCapUsd() != null) {
                            return -1;
                        } else if (o2.getMarketCapUsd() != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> getMarketCapUsd(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {
            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getMarketCapUsd() != null && o2.getMarketCapUsd() != null) {
                        double order1 = Double.parseDouble(o1.getMarketCapUsd());
                        double order2 = Double.parseDouble(o2.getMarketCapUsd());
                        return compareItemsAscending(order1, order2);
                    } else {
                        if (o1.getMarketCapUsd() != null) {
                            return 1;
                        } else if (o2.getMarketCapUsd() != null) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> sortVolAscending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {
            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.get24hVolumeUsd() != null && o2.get24hVolumeUsd() != null) {
                        double order1 = Double.parseDouble(o1.get24hVolumeUsd());
                        double order2 = Double.parseDouble(o2.get24hVolumeUsd());
                        return compareItemsAscending(order1, order2);
                    } else {
                        if (o1.get24hVolumeUsd() != null) {
                            return 1;
                        } else if (o2.get24hVolumeUsd() != null) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }
    public static List<CoinItem> topVolume(){
        List<CoinItem> result = sortVol24hDecending();
        if(result.size() >0 )
        return  result.subList(0,5);
        else
            return result;
    }

    public static List<CoinItem> sort1hDescending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {
            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getPercentChange1h() != null && o2.getPercentChange1h() != null) {
                        double order1 = Double.parseDouble(o1.getPercentChange1h());
                        double order2 = Double.parseDouble(o2.getPercentChange1h());
                        return compareItemsDscending(order1, order2);
                    } else {
                        if (o1.getPercentChange1h() != null) {
                            return -1;
                        } else if (o2.getPercentChange1h() != null) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }

    public static List<CoinItem> sort1hAscending(){
        List<CoinItem> result = new ArrayList<CoinItem>();
        if (ITEMS.size() > 0) {
            result.addAll(ITEMS);
            Collections.sort(result, new Comparator<CoinItem>() {
                public int compare(CoinItem o1, CoinItem o2) {
                    if (o1.getPercentChange1h() != null && o2.getPercentChange1h() != null) {
                        double order1 = Double.parseDouble(o1.getPercentChange1h());
                        double order2 = Double.parseDouble(o2.getPercentChange1h());
                        return compareItemsAscending(order1, order2);
                    } else {
                        if (o1.getPercentChange1h() != null) {
                            return 1;
                        } else if (o2.getPercentChange1h() != null) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        }
        return result;
    }

    public static List<CoinItem> topGainers(){
        List<CoinItem> result = sort24hDecending();
        if(result.size() >0 )
            return  result.subList(0,5);
        else
            return result;
    }

    public static List<CoinItem> topLossers(){
        List<CoinItem> result = sort24hAscending();
        List<CoinItem> result2 = new ArrayList<>();
        int i = 0;
            for(int j =0; j<result.size() && i<5; j++){
                if(result.get(j).getPercentChange24h() != null && i < 5){
                    result2.add(result.get(j));
                    i++;
                }
             }
        return result2;
    }

    private static int compareItemsAscending(double order1, double order2) {
        if (order1 < order2)
            return -1;
        else if (order1 > order2)
            return 1;
        else
            return 0;
    }
    private static int compareItemsDscending(double order1, double order2) {
        if (order1 < order2)
            return 1;
        else if (order1 > order2)
            return -1;
        else
            return 0;
    }
    public static List<CoinItem> searchList(String key){
        List<CoinItem> result = new ArrayList<CoinItem>();
        for(CoinItem item : ITEMS){
            if(item.name.toLowerCase().contains(key.toLowerCase()) || item.getSymbol().toLowerCase().contains(key.toLowerCase())){
                result.add(item);
            }
        }

        return result;
    }

    public static List<CoinItem> getFavorite(Context context){
        List<CoinItem> result = new ArrayList<CoinItem>();
        SharedPreferenceUtil pref = new SharedPreferenceUtil(context);

        ArrayList<String> list =  pref.loadTabs();

        for(CoinItem item : ITEMS){
            if(list.contains(item.symbol)){
                result.add(item);
            }
        }

        return result;
    }

    public static List<CoinItem> getFavorite(Context context,List<String> list){
        List<CoinItem> result = new ArrayList<CoinItem>();

        for(CoinItem item : ITEMS){
            if(list.contains(item.symbol.toLowerCase())){
                result.add(item);
            }
        }

        return result;
    }
    /**
     * A dummy item representing a piece of content.
     */
    public class CoinItem implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("symbol")
        @Expose
        private String symbol;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("price_usd")
        @Expose
        private String priceUsd;
        @SerializedName("price_btc")
        @Expose
        private String priceBtc;
        @SerializedName("24h_volume_usd")
        @Expose
        private String _24hVolumeUsd;
        @SerializedName("market_cap_usd")
        @Expose
        private String marketCapUsd;
        @SerializedName("available_supply")
        @Expose
        private String availableSupply;
        @SerializedName("total_supply")
        @Expose
        private String totalSupply;
        @SerializedName("max_supply")
        @Expose
        private String maxSupply;
        @SerializedName("percent_change_1h")
        @Expose
        private String percentChange1h;
        @SerializedName("percent_change_24h")
        @Expose
        private String percentChange24h;
        @SerializedName("percent_change_7d")
        @Expose
        private String percentChange7d;
        @SerializedName("last_updated")
        @Expose
        private String lastUpdated;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getPriceUsd() {
            return priceUsd;
        }

        public void setPriceUsd(String priceUsd) {
            this.priceUsd = priceUsd;
        }

        public String getPriceBtc() {
            return priceBtc;
        }

        public void setPriceBtc(String priceBtc) {
            this.priceBtc = priceBtc;
        }

        public String get24hVolumeUsd() {
            return _24hVolumeUsd;
        }

        public void set24hVolumeUsd(String _24hVolumeUsd) {
            this._24hVolumeUsd = _24hVolumeUsd;
        }

        public String getMarketCapUsd() {
            return marketCapUsd;
        }

        public void setMarketCapUsd(String marketCapUsd) {
            this.marketCapUsd = marketCapUsd;
        }

        public String getAvailableSupply() {
            return availableSupply;
        }

        public void setAvailableSupply(String availableSupply) {
            this.availableSupply = availableSupply;
        }

        public String getTotalSupply() {
            return totalSupply;
        }

        public void setTotalSupply(String totalSupply) {
            this.totalSupply = totalSupply;
        }

        public String getMaxSupply() {
            return maxSupply;
        }

        public void setMaxSupply(String maxSupply) {
            this.maxSupply = maxSupply;
        }

        public String getPercentChange1h() {
            return percentChange1h;
        }

        public void setPercentChange1h(String percentChange1h) {
            this.percentChange1h = percentChange1h;
        }

        public String getPercentChange24h() {
            return percentChange24h;
        }

        public void setPercentChange24h(String percentChange24h) {
            this.percentChange24h = percentChange24h;
        }

        public String getPercentChange7d() {
            return percentChange7d;
        }

        public void setPercentChange7d(String percentChange7d) {
            this.percentChange7d = percentChange7d;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

    }
}
