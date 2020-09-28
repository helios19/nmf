package com.nmf.assessment.q1.solution.adapter;

import com.nmf.assessment.q1.solution.publisher.StockProcessor;

@FunctionalInterface
public interface PriceProvider {

    void accept(StockProcessor publisher);

}
