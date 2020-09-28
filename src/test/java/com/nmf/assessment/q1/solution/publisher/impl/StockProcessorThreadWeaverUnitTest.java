package com.nmf.assessment.q1.solution.publisher.impl;

import com.google.testing.threadtester.AnnotatedTestRunner;
import com.google.testing.threadtester.ThreadedAfter;
import com.google.testing.threadtester.ThreadedBefore;
import com.google.testing.threadtester.ThreadedMain;
import com.google.testing.threadtester.ThreadedSecondary;
import io.vavr.Tuple;
import io.vavr.Tuple5;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.nmf.assessment.q1.solution.utils.ClassUtils.Ibm;
import static com.nmf.assessment.q1.solution.utils.ClassUtils.Nike;
import static org.junit.Assert.assertEquals;


public class StockProcessorThreadWeaverUnitTest {

    private StockProcessorImpl processor;

    private Tuple5 tuple1 = Tuple.of(Instant.now(),
            new double[][]{{124.59}, {1288.0}},
            new double[][]{{124.74}, {3618}},
            124.64,
            Ibm);

    private Tuple5 tuple2 = Tuple.of(Instant.now(),
            new double[][]{{124.46}, {1569.0}},
            new double[][]{{122.98}, {475}},
            564.76,
            Nike);

    @ThreadedBefore
    public void before() {
        processor = new StockProcessorImpl();
    }

    @ThreadedMain
    public void mainThread() {
        processor.savePrices(tuple1);
    }

    @ThreadedSecondary
    public void secondThread() {
        processor.savePrices(tuple2);
    }

    @ThreadedAfter
    public void after() {
        assertEquals(2, processor.STOCK_QUEUES.size());
    }

    //    @Ignore
    @Test
    public void shouldSavePricesFromSeveralProvidersWithoutConcurrencyIssues() {
        new AnnotatedTestRunner().runTests(this.getClass(), StockProcessorImpl.class);
    }
}