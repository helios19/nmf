package com.nmf.assessment.q1.solution;

import com.nmf.assessment.q1.solution.adapter.PriceProvider;
import com.nmf.assessment.q1.solution.adapter.impl.CallbackPriceProviderAdapter;
import com.nmf.assessment.q1.solution.adapter.impl.LegacyPriceProviderAdapter;
import com.nmf.assessment.q1.solution.consumer.impl.StockConsumerExample;
import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import com.nmf.assessment.q1.solution.model.StockEventType;
import com.nmf.assessment.q1.solution.publisher.StockProcessor;
import com.nmf.assessment.q1.solution.publisher.impl.StockProcessorImpl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.collect.Lists.newArrayList;
import static com.nmf.assessment.q1.solution.utils.ClassUtils._10K_;
import static com.nmf.assessment.q1.solution.utils.ClassUtils._1K_;
import static com.nmf.assessment.q1.solution.utils.ClassUtils._20K_;

/**
 * Program Main Class.
 */
public class Main {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Main method to start the application.
     *
     * @param args Argument array
     */
    public static void main(String[] args) {

        // Initial List of Price Providers
        List<PriceProvider> priceProviders = getPriceProviders();

        // Instantiate a Stock Processor
        StockProcessor stockProcessor = new StockProcessorImpl();

        // Process prices from providers
        executor.submit(() -> processPricesFromProviders(priceProviders, stockProcessor));

        // Stock Consumer example
        StockConsumerExample consumer = new StockConsumerExample();

        // List of Stock Event Strategy (i.e LATEST_PRICE, HIGHEST_PRICE, etc.)
        List<StockEventStrategy> stockEventStrategies = getStockEventStrategies();

        // Subscribe to the Stock Processor instance
        stockProcessor.subscribe(consumer, _1K_, stockEventStrategies);

    }

    /**
     * Returns list of {@link StockEventStrategy} instances {@link StockConsumerExample} has registered to.
     *
     * @return List of Stock Event Strategy.
     */
    private static List<StockEventStrategy> getStockEventStrategies() {
        List<StockEventStrategy> stockEventStrategies = newArrayList(
                StockEventStrategy
                        .builder()
                        .periodSeconds(_10K_)
                        .stockEventType(StockEventType.LATEST_PRICES)
                        .build(),
                StockEventStrategy
                        .builder()
                        .periodSeconds(_20K_)
                        .stockEventType(StockEventType.HIGHEST_PRICES)
                        .build());
        return stockEventStrategies;
    }

    /**
     * Returns a list of {@link PriceProvider} instances emitting stock prices continuously.
     *
     * @return List of Price Providers
     * @see LegacyPriceProviderAdapter
     * @see CallbackPriceProviderAdapter
     */
    private static List<PriceProvider> getPriceProviders() {
        List<PriceProvider> priceProviders = newArrayList(
                new LegacyPriceProviderAdapter(),
                new CallbackPriceProviderAdapter());
        return priceProviders;
    }

    /**
     * Processes stock prices by registering {@link StockProcessor} with the existing Price Providers instantiated
     * as part of {@link #getPriceProviders()} method.
     *
     * @param priceProviders List of Price Providers
     * @param stockProcessor Stock Processor
     */
    private static void processPricesFromProviders(List<PriceProvider> priceProviders, StockProcessor stockProcessor) {
        priceProviders
                .stream()
                .forEach(priceProvider -> priceProvider.accept(stockProcessor));
    }
}
