package com.nmf.assessment.q1.solution.publisher.impl;

import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import io.vavr.Tuple;
import io.vavr.Tuple5;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.time.Instant;

import static com.nmf.assessment.q1.solution.utils.ClassUtils.Ibm;
import static org.junit.Assert.assertEquals;

public class StockProcessorStressUnitTest {

    @Rule
    public ConcurrentRule concurrently = new ConcurrentRule();
    @Rule
    public RepeatingRule rule = new RepeatingRule();

    private static StockProcessorImpl processor = new StockProcessorImpl();

    private Tuple5 tuple1 = Tuple.of(Instant.now(),
            new double[][]{{124.59},{1288.0}},
            new double[][]{{124.74},{3618}},
            124.64,
            Ibm);

    @Test
    @Concurrent(count = 2)
    @Repeating(repetition = 10)
    public void savePrices() {
        processor.savePrices(tuple1);
    }

    @AfterClass
    public static void annotatedTestRunsMultipleTimes() throws InterruptedException {
        assertEquals(1, processor.STOCK_QUEUES.size());
    }

}