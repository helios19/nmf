package com.nmf.assessment.q1.solution.publisher.impl;

import io.vavr.Tuple;
import io.vavr.Tuple5;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static com.nmf.assessment.q1.solution.utils.ClassUtils.Ibm;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public class StockProcessorSimpleUnitTest {

    private Tuple5 tuple1 = Tuple.of(Instant.now(),
            new double[][]{{124.59}, {1288.0}},
            new double[][]{{124.74}, {3618}},
            124.64,
            Ibm);

    @Test
    public void shouldSavePricesFromSeveralProvidersAndAddInvokeStockQueues() throws Exception {
        // given
        StockProcessorImpl processor = spy(StockProcessorImpl.class);
        Map mockStockQueues = mock(ConcurrentMap.class);
        doReturn(mockStockQueues).when(processor).getStockQueues();

        // when
        processor.savePrices(tuple1);

        // then
        then(processor).should(times(5)).getStockQueues();
    }

    @Test
    public void shouldSavePricesFromSeveralProvidersAndAddValueIntoStockQueues() throws Exception {
        // given
        StockProcessorImpl processor = spy(StockProcessorImpl.class);

        // when
        processor.savePrices(tuple1);

        // then
        assertEquals(2, processor.STOCK_QUEUES.size());
    }
}