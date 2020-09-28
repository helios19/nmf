package com.nmf.assessment.q1.solution.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * This class represents the stock event strategy a consumer can register to.
 */
@ToString
@Builder
@Getter
public class StockEventStrategy {
    protected StockEventType stockEventType;
    protected int periodSeconds;
}
