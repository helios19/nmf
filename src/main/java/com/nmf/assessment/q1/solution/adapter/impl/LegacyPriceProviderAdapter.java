package com.nmf.assessment.q1.solution.adapter.impl;

import com.nmf.assessment.q1.provided.LegacyPriceProvider;
import com.nmf.assessment.q1.solution.adapter.PriceProvider;
import com.nmf.assessment.q1.solution.model.StockPrice;
import com.nmf.assessment.q1.solution.publisher.StockProcessor;
import io.vavr.Tuple5;
import lombok.SneakyThrows;

/**
 * Adapter class wrapping the existing {@link LegacyPriceProvider} so that {@link StockProcessor} class can easily
 * listen to in order to receive the stock price feeds.
 *
 * @see LegacyPriceProvider
 * @see StockProcessor
 */
public class LegacyPriceProviderAdapter implements PriceProvider {

    private LegacyPriceProvider legacyPriceProvider = new LegacyPriceProvider();

    @SneakyThrows
    @Override
    public void accept(StockProcessor publisher) {
        while (true) {
            publisher.savePrices(legacyPriceProvider.poll()
                    .parallelStream().map(price -> StockPrice.newPriceTuple(price))
                    .toArray(Tuple5[]::new));
        }
    }
}
