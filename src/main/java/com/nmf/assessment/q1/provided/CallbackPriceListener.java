package com.nmf.assessment.q1.provided;

public interface CallbackPriceListener {
    /**
     * Callback handler. Note this method will get called on a thread which belongs to the CallbackProvider. This
     * thread should be released back to the callback provider as soon as possible so it can continue its work.
     * @param callbackPrice
     */
    void onPrice(CallbackPrice callbackPrice);
}
