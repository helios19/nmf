package com.nmf.assessment.q1.solution.publisher.impl;

import com.nmf.assessment.q1.solution.consumer.StockConsumer;
import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import com.nmf.assessment.q1.solution.publisher.StockProcessor;
import io.vavr.Tuple5;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.java.Log;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinTask;

import static com.nmf.assessment.q1.solution.utils.ClassUtils.BUFFER_CAPACITY;

/**
 * Stock Processor main implementation class allowing consumers to subscribe to stock prices from Price Providers.
 * This class saves stock prices into a custom {@link CircularBuffer} so that consumers can receive prices independently
 * from the price providers and be able to received computed calculations such as Highest, Lowest prices for a stock
 * over a period of time.
 *
 * @see StockProcessor
 * @see CircularBuffer
 * @see StockConsumer
 */
@Log
@ToString
public class StockProcessorImpl implements StockProcessor {

    public static ConcurrentMap<String, CircularBuffer> STOCK_QUEUES = new ConcurrentHashMap<>();

    /**
     * Subscribes to one or several price event types depending on the available publisher strategy.
     *
     * @param consumer   Consumer object
     * @param strategies Strategy object
     */
    @SneakyThrows
    @Override
    public void subscribe(StockConsumer consumer, int intervalPeriod, List<StockEventStrategy> strategies) {

        Objects.requireNonNull(strategies, "Event type strategy must not be null to subscribe");

        StockProcessorTask task = newStockProcessorTask(consumer, intervalPeriod, strategies);

        // loop while waiting for the stock prices to be added to the queue
        while (getStockQueues().size() == 0) {
            Thread.sleep(1000);
        }

        // invoke stock processing task
        ForkJoinTask.invokeAll(task);
    }

    private StockProcessorTask newStockProcessorTask(StockConsumer consumer, int intervalPeriod,
                                                     List<StockEventStrategy> strategies) {
        return StockProcessorTask.builder()
                .consumer(consumer)
                .stockMap(getStockQueues())
                .intervalPeriod(intervalPeriod)
                .strategies(strategies)
                .build();
    }

    @Override
    public void savePrices(Tuple5... tuples) {

        Arrays.stream(tuples).forEach(stockPrice -> {

            Instant time = (Instant) stockPrice._1;
            double[][] bids = (double[][]) stockPrice._2;
            double[][] asks = (double[][]) stockPrice._3;
            double last = (double) stockPrice._4;
            String symbol = (String) stockPrice._5;

            if (getStockQueues().get(symbol) == null) {
                // add new stock price with a concurrent Set sorted on the reverse time order
                getStockQueues().put(symbol,
                        new CircularBuffer(BUFFER_CAPACITY));
            }

            getStockQueues().get(symbol)
                    .offer(time,
                            bids,
                            asks,
                            last,
                            symbol);

        });
    }

    protected ConcurrentMap<String, CircularBuffer> getStockQueues() {
        return STOCK_QUEUES;
    }
}
