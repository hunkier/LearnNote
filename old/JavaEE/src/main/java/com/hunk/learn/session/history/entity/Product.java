package com.hunk.learn.session.history.entity;



/**
 * Created by hunk on 2015/7/31.
 */
public class Product {
    private String id;
    private String proName;
    private String proType;
    private double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Product() {
        super();
    }

    public Product(String id, String proName, String proType, double price) {
        this.id = id;
        this.proName = proName;
        this.proType = proType;
        this.price = price;
    }
}
