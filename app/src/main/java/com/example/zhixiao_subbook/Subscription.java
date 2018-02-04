package com.example.zhixiao_subbook;

import java.util.Date;

/**
 * Created by Helen on 2018/1/31.
 */

public class Subscription {
    private String name;
    //private Date date;
    private String comment;
    private String fee;
    private String date;

    Subscription(String name, String date, String fee, String comment) {
        this.name = name;
        this.date = date;
        this.fee = fee;
        this.comment = comment;
    }



    public String getName() { return name; }

    public void setName(String name) throws NameTooLongException {
        if (name.length() < 20) {
            this.name = name;
        }
        else {
            throw new NameTooLongException();
        }
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getFee() { return fee; }

    public void setFee(String fee) { this.fee = fee; }

    public String getComment() { return comment; }

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() < 30) {
            this.comment = comment;
        }
        else {
            throw new CommentTooLongException();
        }
    }

    public String toString() { return name + "\n" + date + "\n" + fee + "\n" + comment; }

}


