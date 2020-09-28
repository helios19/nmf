package com.nmf.assessment.q1.solution.model;

import com.nmf.assessment.q1.provided.CallbackPrice;
import com.nmf.assessment.q1.provided.Price;
import com.nmf.assessment.q1.solution.publisher.impl.CircularBuffer;
import io.vavr.Tuple;
import io.vavr.Tuple5;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.time.Instant;

import static com.nmf.assessment.q1.solution.utils.ClassUtils.TICKERS_MAPPING;

/**
 * Stock Price object representing a consolidated version of the {@link Price} and
 * {@link CallbackPrice} class and used in the custom {@link CircularBuffer}.
 *
 * @see CircularBuffer
 * @see Price
 * @see CallbackPrice
 */
@Log
@Builder
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class StockPrice {

    private Instant time;
    private double[][] bids;
    private double[][] asks;
    private double last;
    private String symbol;

    /**
     * Constructor method building new Tuple of 5 elements from a {@link Price} input argument object.
     *
     * @param price Price
     * @return Tuple of 5 elements
     */
    public static Tuple5 newPriceTuple(Price price) {

        // instant
        final Instant instant = Instant.ofEpochMilli(price.getTimeMillis());

        // bids
        double[][] bids = new double[1][2];
        bids[0][0] = price.getBidPrice();
        bids[0][1] = price.getBidVolume();

        // asks
        double[][] asks = new double[1][2];
        asks[0][0] = price.getAskPrice();
        asks[0][1] = price.getAskVolume();

        // last price
        double last = price.getLastPrice();

        // symbol
        String symbol = TICKERS_MAPPING.get(price.getStockId());


        return Tuple.of(
                instant,
                bids,
                asks,
                last,
                symbol);
    }
}
