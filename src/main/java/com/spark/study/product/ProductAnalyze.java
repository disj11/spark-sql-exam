package com.spark.study.product;

public class ProductAnalyze {
    private long id;
    private long view;
    private long cart;
    private long purchase;
    private double revenue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public long getCart() {
        return cart;
    }

    public void setCart(long cart) {
        this.cart = cart;
    }

    public long getPurchase() {
        return purchase;
    }

    public void setPurchase(long purchase) {
        this.purchase = purchase;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", view=" + view +
                ", cart=" + cart +
                ", purchase=" + purchase +
                ", revenue=" + revenue +
                '}';
    }
}
