package com.nmf.assessment.q1.provided;

import java.time.Instant;

public class CallbackPrice {

    final Instant time;
    final double[][] bids;
    final double[][] asks;
    final double last;
    final String symbol;

    public CallbackPrice(Instant time, double[][] bids, double[][] asks, double last, String symbol) {
        this.time = time;
        this.bids = bids;
        this.asks = asks;
        this.last = last;
        this.symbol = symbol;
    }

    public Instant getTime() {
        return time;
    }

    public double[][] getBids() {
        return bids;
    }

    public double[][] getAsks() {
        return asks;
    }

    public double getLast() {
        return last;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "CallbackPrice{" +
                "time=" + time +
                "," + formatPrices(bids, "Bids:") +
                "," + formatPrices(asks, "Asks:") +
                ", last=" + last +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    private String formatPrices(double[][] prices, String header) {
        StringBuilder sb = new StringBuilder();
        sb.append(header).append("=[");
        for (double[] price : prices) {
            sb.append(price[0]).append("-").append(price[1]).append(",");
        }
        sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",")+1, "]");
        return sb.toString();
    }
}
