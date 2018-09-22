package com.tregix.cryptocurrencytracker.Model;

public class BlockfolioUpdateItem {

        private String coin;
        private String exchange;
        private String pair;
        private String quantity;
        private String price ;
        private int type ;

        public BlockfolioUpdateItem(String coin, String exchange, String pair, String quantity,String price, int type) {
            this.exchange = exchange;
            this.pair = pair;
            this.quantity = quantity;
            this.price = price;
            this.type = type;
            this.coin = coin;
        }
}
