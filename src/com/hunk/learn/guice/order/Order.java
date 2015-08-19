package com.hunk.learn.guice.order;

import java.util.Date;

/**
 * Created by hunk on 2015/8/19.
 */
public class Order {
    private int id;
    private String customer;
    private Date createDate;

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
