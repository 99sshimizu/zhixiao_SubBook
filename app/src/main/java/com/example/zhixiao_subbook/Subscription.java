package com.example.zhixiao_subbook;

/**
 * @author Helen
 * @version 1.0
 * @see NewSub
 */
public class Subscription {
    private String name;
    private String comment;
    private String fee;
    private String date;

    /**
     * Creates a Subscription object.
     *
     * @param name Name of subscription.
     * @param date Start date of subscription.
     * @param fee Monthly fee of subscription.
     * @param comment Comments on this subscription.
     */
    Subscription(String name, String date, String fee, String comment) {
        this.name = name;
        this.date = date;
        this.fee = fee;
        this.comment = comment;
    }

    /**
     * Gets the name of Subscription.
     *
     * @return Name of Subscription.
     */
    public String getName() { return name; }

    /**
     * Sets the name of Subscription.
     *
     * @param name Name of Subscription.
     * @throws NameTooLongException Name of Subscription is more than 20 characters.
     */
    public void setName(String name) throws NameTooLongException {
        if (name.length() < 20) {
            this.name = name;
        }
        else {
            throw new NameTooLongException();
        }
    }

    /**
     * Gets the start date of Subscription.
     *
     * @return Start date of Subscription.
     */
    public String getDate() { return date; }

    /**
     * Sets the start date of Subscription.
     *
     * @param date Start date of Subscription.
     */
    public void setDate(String date) { this.date = date; }

    /**
     * Gets the monthly fee of Subscription.
     *
     * @return Monthly fee of Subscription.
     */
    public String getFee() { return fee; }

    /**
     * Sets the monthly fee of Subscription.
     *
     * @param fee Monthly fee of Subscription.
     */
    public void setFee(String fee) { this.fee = fee; }

    /**
     * Gets the comments for Subscription.
     *
     * @return Comments on this Subscription.
     */
    public String getComment() { return comment; }

    /**
     * Sets the comments for Subscription.
     *
     * @param comment Comments on this Subscription.
     */
    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() < 30) {
            this.comment = comment;
        }
        else {
            throw new CommentTooLongException();
        }
    }

    /**
     * Displays the Subscription object.
     *
     * @return String representation of the Subscription object.
     */
    public String toString() { return "Name: " + name + "\n" + "Start date: " + date + "\n"
            + "Monthly Charge: " + fee /*+ "\n" + comment*/; }

}


