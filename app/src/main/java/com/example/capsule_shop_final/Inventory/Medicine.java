package com.example.capsule_shop_final.Inventory;

public class Medicine {
    private String desc;
    private int price;
    private int qty;

    public Medicine() {
    }

    public Medicine(String desc, int price, int qty) {
        this.desc = desc;
        this.price = price;
        this.qty = qty;
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
