package com.example.capsule_shop_final.Orders;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Medicine implements Serializable {
    private String desc;
    private int price;
    private int qty;
    private boolean isChecked;


    public Medicine() {
    }

    @Exclude
    public boolean isChecked() {
        return isChecked;
    }

    @Exclude
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
