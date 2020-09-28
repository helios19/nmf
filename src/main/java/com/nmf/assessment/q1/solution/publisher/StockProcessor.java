package com.nmf.assessment.q1.solution.publisher;

import com.nmf.assessment.q1.solution.consumer.StockConsumer;
import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import io.vavr.Tuple5;

import java.util.List;

/**
 * Stock processor interface providing two main functionalities to save prices and register consumers according to a set
 * of {@link StockEventStrategy} instances.
 *
 * @see StockConsumer
 * @see StockEventStrategy
 */
public interface StockProcessor {
    void savePrices(Tuple5... prices);

    void subscribe(StockConsumer consumer, int intervalPeriod, List<StockEventStrategy> strategies);
}
