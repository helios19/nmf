package com.nmf.assessment.q1.solution.consumer.impl;

import com.nmf.assessment.q1.solution.consumer.StockConsumer;
import com.nmf.assessment.q1.solution.model.StockEventResult;
import com.nmf.assessment.q1.solution.model.StockEventStrategy;
import lombok.extern.java.Log;

import java.util.List;

/**
 * Class consumer example holding the logic to receive stock price result events.
 *
 * @see StockEventResult
 * @see StockEventStrategy
 */
@Log
public class StockConsumerExample implements StockConsumer {
    /**
     * Receives the stock price events according to a list of strategies a consumer would have registered to.
     *
     * @param stockEventResult List of stock price events
     */
    @Override
    public void receive(List<StockEventResult> stockEventResult) {
        // print out the list of event received from the publisher
        log.info("Events received : " + stockEventResult.toString());

        // process each event based on their type
        stockEventResult.stream().forEach(stockEventResult1 -> {
            switch (stockEventResult1.getStrategy().getStockEventType()) {
                case LATEST_PRICES: // do something with the latest prices
                case HIGHEST_PRICES:// do something with the highest prices
                default:
            }
        });
    }
}
