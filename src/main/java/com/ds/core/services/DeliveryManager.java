package com.ds.core.services;

import com.ds.core.models.Order;

public class DeliveryManager {

    private MapService mapService;

    public DeliveryManager(MapService mapService) {
        this.mapService = mapService;
    }

    public void assignPriority(Order order) {

        double distance = mapService.getDistance(order);

        int priority = 0;

        if(distance > 10){
            priority += 5;
        }

        order.setPriority(priority);
    }
}
