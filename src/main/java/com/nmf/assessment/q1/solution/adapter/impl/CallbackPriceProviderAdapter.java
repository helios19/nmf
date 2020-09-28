package com.nmf.assessment.q1.solution.adapter.impl;

import com.nmf.assessment.q1.provided.CallbackPriceProvider;
import com.nmf.assessment.q1.solution.adapter.PriceProvider;
import com.nmf.assessment.q1.solution.publisher.StockProcessor;
import io.vavr.Tuple;

/**
 * Adapter class wrapping the existing {@link CallbackPriceProvider} so that {@link StockProcessor} class can easily
 * listen to in order to receive the stock price feeds.
 *
 * @see CallbackPriceProvider
 * @see StockProcessor
 */
public class CallbackPriceProviderAdapter implements PriceProvider {

    private CallbackPriceProvider callbackPriceProvider = new CallbackPriceProvider();

    @Override
    public void accept(final StockProcessor publisher) {

        callbackPriceProvider.addListener(callbackPrice -> {

            publisher.savePrices(
                    Tuple.of(
                            callbackPrice.getTime(),
                            callbackPrice.getBids(),
                            callbackPrice.getAsks(),
                            callbackPrice.getLast(),
                            callbackPrice.getSymbol()));

        });

    }
}
