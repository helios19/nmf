package com.nmf.assessment.q1.solution.publisher.impl;

import com.nmf.assessment.q1.solution.consumer.StockConsumer;
import com.nmf.assessment.q1.solution.model.StockEventResult;
import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import com.nmf.assessment.q1.solution.model.StockPrice;
import com.nmf.assessment.q1.solution.publisher.StockProcessor;
import io.vavr.control.Try;
import lombok.Builder;
import lombok.extern.java.Log;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.nmf.assessment.q1.solution.utils.ClassUtils.getHighestPrice;

/**
 * This class represents the main stock processor task called from {@link StockProcessor} implementation class. It is a
 * subclass of {@link RecursiveAction} so that it can be executed and take advantage of the {@link ForkJoinPool}
 * stealing-work algorithm to improve the efficiency and performance of this task.
 *
 * <p>In addition, this class spawns additional sub tasks also executed by {@link ForkJoinPool} that corresponds to the
 * event strategies a consumer would have registered with. The list of those strategy stock price events can be found in
 * {@link StockEventStrategy} class.
 *
 * @see StockEventStrategy
 * @see RecursiveAction
 */
@Log
@Builder
public class StockProcessorTask extends RecursiveAction {

    private ConcurrentMap<String, CircularBuffer> stockMap;
    private List<StockEventStrategy> strategies;
    private StockConsumer consumer;
    private int intervalPeriod;
    private boolean interrupt;

    @Override
    protected void compute() {
        try {
            while (!isInterrupt()) {

                // process stock event before publishing to consumer
                List<StockEventResult> stockEventResult = process();

                // publish stock event to consumer
                getConsumer().receive(stockEventResult);

                // pause during X interval period
                Thread.sleep(intervalPeriod);
            }
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "An error occurred while processing stock prices", e);
            interrupt = true;
        }
    }

    protected StockConsumer getConsumer() {
        return consumer;
    }

    protected boolean isInterrupt() {
        return interrupt;
    }

    /**
     * Processes the existing list of tasks a consumer would have subscribed to and return a list of event to publish.
     *
     * @return List of Stock event result
     */
    protected List<StockEventResult> process() {

        // forks and executes the given tasks using ForkJoinTask
        return invokeAll(createSubTasks())
                .stream()
                .flatMap(stockPriceTask -> Try.of(stockPriceTask::get).get().stream())
                .collect(Collectors.toList());
    }

    /**
     * Create a list of stock price sub tasks instances of {@link java.util.concurrent.RecursiveTask} and that will be
     * executed on the {@link ForkJoinPool}.
     *
     * @return Result list of stock price events
     */
    protected List<StockPriceTask> createSubTasks() {
        return strategies.stream().map(strategy -> {

            BiFunction<ConcurrentMap<String, CircularBuffer>, StockEventStrategy, List<StockEventResult>>
                    function = null;

            switch (strategy.getStockEventType()) {
                case LATEST_PRICES:
                    function = StockProcessorTask::latestStockPrice;
                    break;
                case HIGHEST_PRICES:
                    function = StockProcessorTask::highestStockPrice;
                    break;
                default:
            }

            return StockPriceTask.builder()
                    .function(function)
                    .strategy(strategy)
                    .stockQueues(stockMap)
                    .build();

        }).collect(Collectors.toList());
    }

    /**
     * Strategy method that processes stock prices and returns a list of events with the highest prices of each stock
     * based on the interval chosen by the consumers.
     *
     * @param stockQueues Stock queues
     * @param strategy    Strategy holding the period over which the highest price are calculated
     * @return List of stock event result
     */
    private static final List<StockEventResult> highestStockPrice(ConcurrentMap<String, CircularBuffer> stockQueues,
                                                                  StockEventStrategy strategy) {

        // get the highest price over a period of time

        return stockQueues.keySet().stream().map(s -> {

            CircularBuffer buffer = stockQueues.get(s);

            // get the current buffer index
            int currentIndex = buffer.getCursorIndex();

            // if empty buffer return an empty event
            if (currentIndex < 0) {
                return StockEventResult.builder().build();
            }

            // get the highest price from the buffer over a period interval
            double highestPrice = getHighestPrice(strategy, buffer, currentIndex);

            return StockEventResult
                    .builder()
                    .symbol(s)
                    .time(Instant.now())
                    .price(highestPrice)
                    .strategy(strategy)
                    .build();


        }).collect(Collectors.toList());


    }

    /**
     * Strategy method that processes stock prices and returns a list of events with the latest prices of each stock.
     *
     * @param stockQueues Stock queues
     * @param strategy    Strategy holding the period over which the highest price are calculated
     * @return List of stock event result
     */
    private static final List<StockEventResult> latestStockPrice(ConcurrentMap<String, CircularBuffer> stockQueues,
                                                                 StockEventStrategy strategy) {

        return stockQueues.keySet().stream().map(s -> {

            CircularBuffer buffer = stockQueues.get(s);

            // TODO: Potential stale data retrieved from buffer
            StockPrice lastStockPrice = buffer.poll();

            return StockEventResult
                    .builder()
                    .symbol(lastStockPrice.getSymbol())
                    .price(lastStockPrice.getLast())
                    .time(lastStockPrice.getTime())
                    .bids(lastStockPrice.getBids())
                    .asks(lastStockPrice.getAsks())
                    .strategy(strategy)
                    .build();

        }).collect(Collectors.toList());
    }

}
