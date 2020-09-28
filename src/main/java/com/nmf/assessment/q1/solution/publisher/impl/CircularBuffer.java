package com.nmf.assessment.q1.solution.publisher.impl;

import com.nmf.assessment.q1.solution.model.StockPrice;
import lombok.ToString;
import lombok.extern.java.Log;

import java.time.Instant;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.locks.StampedLock;
import java.util.logging.Level;

/**
 * Custom circular buffer implementation class that is designed to hold the list of prices for a particular stock. This
 * buffer class is bounded and is using a CAS {@link StampedLock} mechanism to allow several concurrent consumers read
 * access, while permitting only one producer at a time when adding elements to this data structure.
 *
 * @see StampedLock
 */
@Log
public class CircularBuffer {

    private static final int DEFAULT_CAPACITY = 8;

    private final int capacity;
    private final StockPrice[] data;
    private volatile int writeSequence;
    private volatile int readSequence;

    private final StampedLock lock = new StampedLock();

    /**
     * CircularBuffer constructor accepting size capacity value.
     *
     * @param capacity Circular Buffer capacity
     */
    @SuppressWarnings("unchecked")
    public CircularBuffer(int capacity) {

        this.capacity = (capacity < 1) ? DEFAULT_CAPACITY : capacity;
        this.data = new StockPrice[capacity];

        for (int i = 0; i < capacity; i++) {
            data[i] = StockPrice.builder().build();
        }

        this.readSequence = 0;
        this.writeSequence = -1;
    }

    /**
     * Add new element into the Circular Buffer.
     *
     * @param time   Instant time
     * @param bids   bids prices
     * @param asks   asks prices
     * @param last   latest price
     * @param symbol Stock symbol
     * @return True is the insertion operation has succeeded, false otherwise.
     */
    public boolean offer(Instant time, double[][] bids, double[][] asks, double last, String symbol) {

        long stamp = lock.writeLock();

        try {
            if (isNotFull()) {

                int nextWriteSeq = writeSequence + 1;

                StockPrice element = data[nextWriteSeq % capacity];

                // rewrite existing stockPrice element value only if the new values are more recent
                // than the existing prices. This is to address the potential stale prices from the feeds.
                if (element.getTime() == null || time.compareTo(element.getTime()) > 0) {
                    element.setTime(time);
                    element.setBids(bids);
                    element.setAsks(asks);
                    element.setLast(last);
                    element.setSymbol(symbol);
                    data[nextWriteSeq % capacity] = element;
                }

                writeSequence++;
                return true;
            }
        } catch (Exception ex) {
            log.log(Level.SEVERE, "An error occured while inserting into the buffer : ", ex);
        } finally {
            lock.unlockWrite(stamp);
        }

        return false;
    }

    /**
     * Returns StockPrice element retrieved from current position.
     *
     * @return StockPrice
     */
    public StockPrice poll() {

        long stamp = lock.readLock();

        try {
            if (isNotEmpty()) {

                StockPrice nextValue = data[readSequence % capacity];
                readSequence++;
                return nextValue;
            }
        } finally {
            lock.unlockRead(stamp);
        }

        return null;
    }

    /**
     * Returns StockPrice element currently located at current position.
     * Note that during this operation the current index is not incremented.
     *
     * @return StockPrice
     */
    public StockPrice peek() {

        long stamp = lock.readLock();

        try {
            if (isNotEmpty()) {

                StockPrice peekValue = data[readSequence % capacity];

                return peekValue;
            }
        } finally {
            lock.unlockRead(stamp);
        }

        return null;
    }

    /**
     * Returns a {@link StockPriceIterator} instance to iterate around the Cicular Buffer data.
     *
     * @return StockPriceIterator
     */
    public StockPriceIterator iterator() {

        long stamp = lock.readLock();

        try {
            return new StockPriceIterator(writeSequence, capacity, data);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    // Return the value at a given index
    public StockPrice get(int index) {
        return data[index];
    }

    public int getCursorIndex() {
        return writeSequence;
    }

    public int capacity() {
        return capacity;
    }

    public int size() {

        return (writeSequence - readSequence) + 1;
    }

    public boolean isEmpty() {

        return writeSequence < readSequence;
    }

    public boolean isFull() {

        return size() >= capacity;
    }

    private boolean isNotEmpty() {

        return !isEmpty();
    }

    private boolean isNotFull() {

        return !isFull();
    }

    @ToString
    public final class StockPriceIterator implements Iterator<StockPrice> {
        private final StockPrice[] elements;
        private int capacity;
        private int writeSequence;
        private volatile int nextIndex;

        /**
         * Constructor accepting three argument namely {@code writeSequence}, {@code capacity} and {@code elements}.
         *
         * @param writeSequence Write sequence position
         * @param capacity Capacity of the Circular Buffer
         * @param elements List of elements to iterate over
         */
        @SafeVarargs
        public StockPriceIterator(int writeSequence, int capacity, StockPrice... elements) {
            this.elements = Objects.requireNonNull(elements);
            this.capacity = capacity;
            this.writeSequence = writeSequence;
            this.nextIndex = writeSequence + 1;
        }

        /**
         * Checks whether the next position contains a non-null element.
         *
         * @return True if the next element is not null false otherwise.
         */
        public boolean hasNext() {

            if (writeSequence < 0) {
                return false;
            }

            long stamp = lock.readLock();

            try {
                return ((nextIndex) % capacity) != (writeSequence % capacity);
            } finally {
                lock.unlockRead(stamp);
            }
        }

        /**
         * Returns next {@link StockPrice} element.
         *
         * @return StockPrice element
         */
        public StockPrice next() {

            long stamp = lock.readLock();

            try {
                if (hasNext()) {
                    StockPrice stockPrice = elements[nextIndex % capacity];
                    nextIndex++;
                    return stockPrice;
                }
            } finally {
                lock.unlockRead(stamp);
            }

            return null;
        }
    }
}
