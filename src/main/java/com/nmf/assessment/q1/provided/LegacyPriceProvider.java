package com.nmf.assessment.q1.provided;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.nmf.assessment.q1.provided.Helper.*;

public class LegacyPriceProvider {

    private final static Map<Integer, String> tickerMappings = new HashMap<>();
    static {
        tickerMappings.put(1000, Disney);
        tickerMappings.put(1001, Ibm);
        tickerMappings.put(1002, Nike);
        tickerMappings.put(1003, ExxonMobil);
        tickerMappings.put(1004, JohnsonJohnson);
    }
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final Map<Integer, Double> lastMidPrices = new HashMap<>();
    private final RingBuffer buffer = new RingBuffer(64);
    private final List<Price> prices = new ArrayList<>(64);


    public LegacyPriceProvider() {
        executor.scheduleAtFixedRate(this::generatePrices, 10, 10, TimeUnit.MILLISECONDS);
    }
    /**
     * Calling poll will synchronously return any undelivered prices. These price objects are retained by the service and
     * can be re-used for subsequent incoming prices. It's important to avoid dirty reads you consume information from the objects
     * quickly.
     *
     * @return prices
     */
    public List<Price> poll() {
        synchronized (buffer) {
            while (buffer.hasNext()) {
                prices.add(buffer.readNext());
            }
        }
        return prices;
    }

    public Map<Integer, String> getTickerMappings() {
        return Collections.unmodifiableMap(tickerMappings);
    }

    private void generatePrices() {
        synchronized (buffer) {
            for (int id : tickerMappings.keySet()) {
                if(!lastMidPrices.containsKey(id)) {
                    lastMidPrices.put(id, startingPrices.get(tickerMappings.get(id)));
                }
                double lastMid = lastMidPrices.get(id);
                double newMid = random(lastMid - 0.1, lastMid + 0.1);
                double[] bidAsk = getBidAskFromMid(newMid);
                buffer.write(id, bidAsk[0], randomInt(1, 10000), bidAsk[1], randomInt(1, 10000),
                        lastMid, System.currentTimeMillis());
            }
        }
    }

    /**
     * Non thread-safe simple ring-buffer
     */
    static class RingBuffer {

        private final Price[] buffer;
        private int writeIndex = 0;
        private int readIndex = 0;

        public RingBuffer(int size) {
            buffer = new Price[size];
            for(int i=0; i<buffer.length; i++) {
                buffer[i] = new Price();
            }
        }

        public void write(int stockId, double bidPrice, int bidVolume, double askPrice, int askVolume,
                          double lastPrice, long timeMillis) {
            Price p = buffer[writeIndex];
            p.setStockId(stockId);
            p.setBidPrice(bidPrice);
            p.setBidVolume(bidVolume);
            p.setAskPrice(askPrice);
            p.setAskVolume(askVolume);
            p.setLastPrice(lastPrice);
            p.setTimeMillis(timeMillis);
            writeIndex = incrementCounter(writeIndex);
            if(writeIndex == readIndex) {
                System.err.println("buffer full, discarding old prices");
            }
        }

        public Price readNext() {
            if(!hasNext()){
                return null;
            }
            Price p = buffer[readIndex];
            readIndex = incrementCounter(readIndex);
            return p;
        }

        public boolean hasNext() {
            return readIndex != writeIndex;
        }

        private int incrementCounter(int counter) {
            return counter == buffer.length-1 ? 0 : counter+1;
        }

    }

}
