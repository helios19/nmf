package com.nmf.assessment.q1.solution.model;

/**
 * Enum class holding the list of available stock event type a consumer is expecting to register and receive when
 * subscribing. Each of this type will have a corresponding implementation function holding the calculation logic.
 */
public enum StockEventType {
    LATEST_PRICES,
    HIGHEST_PRICES,
    LOW_PRICES,
    MOVING_AVERAGE,
    VOLATILITY;
}
