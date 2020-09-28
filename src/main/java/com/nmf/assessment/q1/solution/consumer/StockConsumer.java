package com.nmf.assessment.q1.solution.consumer;

import com.nmf.assessment.q1.solution.model.StockEventResult;

import java.util.List;

public interface StockConsumer {

    void receive(List<StockEventResult> stockEventResult);
}
