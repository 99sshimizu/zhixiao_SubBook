package com.example.zhixiao_subbook;

/**
 * @author Helen
 * @version 1.0
 * @see Subscription
 */
public class NewSub extends Subscription {

    /**
     * Creates a new subscription.
     *
     * @param name Name of new subscription.
     * @param date Start date of new subscription.
     * @param fee Monthly fee of new subscription.
     * @param comment Comments on the new subscription.
     */
    NewSub(String name, String date, String fee, String comment) {
        super(name, date, fee, comment);

    }
}
