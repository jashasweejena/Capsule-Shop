package com.example.capsule_shop_final.Orders;

import java.util.List;

public class Order {
    String name;
    String address;
    List<Medicine> medicines;

    public Order(String name, String address, List<Medicine> medicines) {
        this.name = name;
        this.address = address;
        this.medicines = medicines;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
