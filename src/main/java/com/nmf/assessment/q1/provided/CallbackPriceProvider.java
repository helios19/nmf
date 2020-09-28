package com.nmf.assessment.q1.provided;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.nmf.assessment.q1.provided.Helper.*;

public class CallbackPriceProvider {

    private final List<CallbackPriceListener> listeners = new LinkedList<>();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private final String[] symbols = new String[] {Disney, Ibm, Nike, ExxonMobil, JohnsonJohnson};
    private final Map<String, Double> lastPrices = new HashMap<>();
    private static final int numLevels = 5;

    public CallbackPriceProvider() {
        executor.schedule(this::generateAndSend, randomInt(0, 100), TimeUnit.MILLISECONDS);
    }

    private void generateAndSend() {
        try {
            int index = randomInt(0, symbols.length);
            String sym = symbols[index];
            if (!lastPrices.containsKey(sym)) {
                lastPrices.put(sym, startingPrices.get(sym));
            }
            double lastMid = lastPrices.get(sym);
            double newMid = random(lastMid - 0.1, lastMid + 0.1);
            double[] bidAsk = getBidAskFromMid(newMid);
            double[][] bids = new double[numLevels][2];
            double[][] asks = new double[numLevels][2];
            double bestBid = bidAsk[0];
            double bestAsk = bidAsk[0];
            for (int i=0; i<numLevels; i++) {
                bids[i][0] = roundToTick(bestBid);
                bids[i][1] = randomInt(1, 10000);
                bestBid -= .01;

                asks[i][0] = roundToTick(bestAsk);
                asks[i][1] = randomInt(1, 10000);
                bestAsk += .01;
            }
            listeners.forEach(l -> l.onPrice(new CallbackPrice(Instant.now(), bids, asks, lastMid, sym)));
        } finally {
            executor.schedule(this::generateAndSend, randomInt(10, 250), TimeUnit.MILLISECONDS);
        }
    }

    public void addListener(CallbackPriceListener listener) {
        listeners.add(listener);
    }
    
}
