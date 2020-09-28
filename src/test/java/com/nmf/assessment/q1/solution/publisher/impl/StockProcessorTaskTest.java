package com.nmf.assessment.q1.solution.publisher.impl;

import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class StockProcessorTaskTest {

    @Test
    public void shouldComputeTask() {
        // given
        StockProcessorTask stockProcessorTask = spy(StockProcessorTask.class);

        // when
        stockProcessorTask.compute();

        // then
        then(stockProcessorTask).should(times(1)).process();
        then(stockProcessorTask).should(times(1)).getConsumer();
    }
}