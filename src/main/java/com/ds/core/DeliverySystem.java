package com.ds.core;

import com.ds.core.datastructure.OrderHeap;
import com.ds.core.models.Order;
import com.ds.core.services.DeliveryManager;
public class DeliverySystem {

    private DeliveryManager deliveryManager;
    private OrderHeap orderHeap;

    public DeliverySystem(DeliveryManager deliveryManager,
                          OrderHeap orderHeap) {

        this.deliveryManager = deliveryManager;
        this.orderHeap = orderHeap;
    }

    public void addOrder(Order order) {

        deliveryManager.assignPriority(order);

        orderHeap.insert(order);
    }
}