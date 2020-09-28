package com.nmf.assessment.q1.solution.publisher.impl;

import com.nmf.assessment.q1.solution.model.StockEventResult;
import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import lombok.Builder;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiFunction;

/**
 * This class represents the subtask implementation class instantiated by the main {@link StockProcessorTask} task
 * processing class. The resulting objects of this class are expected to be executed using
 * {@link java.util.concurrent.ForkJoinTask} class.
 *
 * @see RecursiveTask
 * @see java.util.concurrent.ForkJoinTask
 */
@Builder
public class StockPriceTask extends RecursiveTask<List<StockEventResult>> {

    private BiFunction<ConcurrentMap<String, CircularBuffer>, StockEventStrategy, List<StockEventResult>> function;
    private StockEventStrategy strategy;
    private ConcurrentMap<String, CircularBuffer> stockQueues;

    /**
     * Computes a logic compounded into the {@link #function} instance variable according to a {@link #strategy} to be
     * executed against {@link #stockQueues} prices.
     *
     * @return List of stock event result
     */
    @Override
    protected List<StockEventResult> compute() {

        // compute the price to publish (latest, high, low, average, etc.)
        List<StockEventResult> result = function.apply(stockQueues, strategy);

        return result;
    }
}
