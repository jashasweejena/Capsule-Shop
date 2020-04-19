package com.example.capsule_shop_final.Interface;

import com.example.capsule_shop_final.Orders.Order;

import java.util.List;

public interface OrderCallback {
    public void onOrdersFetched(List<Order> orderList);
}
