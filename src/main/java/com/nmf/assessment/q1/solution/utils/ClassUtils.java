package com.nmf.assessment.q1.solution.utils;

import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import com.nmf.assessment.q1.solution.publisher.impl.CircularBuffer;

import java.time.Instant;
import java.util.Map;


public interface ClassUtils {
    String Disney = "DIS";
    String Ibm = "IBM";
    String Nike = "NKE";
    String ExxonMobil = "XOM";
    String JohnsonJohnson = "JNJ";
    String[] SYMBOLS = new String[]{Disney, Ibm, Nike, ExxonMobil, JohnsonJohnson};

    Map<Integer, String> TICKERS_MAPPING = Map
            .of(1000, Disney,
                    1001, Ibm,
                    1002, Nike,
                    1003, ExxonMobil,
                    1004, JohnsonJohnson);

    int BUFFER_CAPACITY = 64;

    int _1K_ = 1000;
    int _10K_ = 10000;
    int _20K_ = 20000;


    /**
     * Returns the highest price according to input arguments {@link StockEventStrategy}, {@link CircularBuffer} and
     * {@code currentIndex}.
     *
     * @param strategy Stock event strategy
     * @param buffer Cicular Buffer
     * @param currentIndex current index
     * @return Highest price over a period of time retrieved from strategy chosen by consumer
     */
    static double getHighestPrice(StockEventStrategy strategy, CircularBuffer buffer, int currentIndex) {
        int count = 0;
        double highestPrice = 0;
        int capacity = buffer.capacity();
        Instant currentInstant = buffer.get(currentIndex % capacity).getTime();

        // generate the date limit from the interval in second
        Instant dateLimit = Instant.ofEpochSecond(currentInstant.getEpochSecond() - strategy.getPeriodSeconds());

        while (count++ < capacity
                && (currentInstant != null && currentInstant.compareTo(dateLimit) > 0)) {

            double tempPrice = buffer.get(currentIndex % capacity).getLast();
            currentInstant = buffer.get(currentIndex % capacity).getTime();

            if (tempPrice > highestPrice) {
                highestPrice = tempPrice;
            }

            currentIndex++;

        }
        return highestPrice;
    }


}
