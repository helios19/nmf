package com.nmf.assessment.q1.solution.model;

import com.nmf.assessment.q1.solution.publisher.StockProcessor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Stock event result class, a consumer receives after subscribing to the {@link StockProcessor} class.
 *
 * @see StockEventStrategy
 */
@Builder
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class StockEventResult {
    private Instant time;
    private String symbol;
    private double price;
    private double[][] bids;
    private double[][] asks;
    private StockEventStrategy strategy;
}
