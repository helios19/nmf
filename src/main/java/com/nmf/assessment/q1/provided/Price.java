package com.nmf.assessment.q1.provided;

public class Price {

    private int stockId;
    private double bidPrice;
    private int bidVolume;
    private double askPrice;
    private int askVolume;
    private double lastPrice;
    private long timeMillis;

    public Price() {
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public int getBidVolume() {
        return bidVolume;
    }

    public void setBidVolume(int bidVolume) {
        this.bidVolume = bidVolume;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public void setAskPrice(double askPrice) {
        this.askPrice = askPrice;
    }

    public int getAskVolume() {
        return askVolume;
    }

    public void setAskVolume(int askVolume) {
        this.askVolume = askVolume;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    @Override
    public String toString() {
        return "Price{" +
                "stockId=" + stockId +
                ", bidPrice=" + bidPrice +
                ", bidVolume=" + bidVolume +
                ", askPrice=" + askPrice +
                ", askVolume=" + askVolume +
                ", lastPrice=" + lastPrice +
                ", lastPrice=" + timeMillis +
                '}';
    }
}
