package com.example.grocerystore.service;

import com.example.grocerystore.entity.OrderEntity;
import com.example.grocerystore.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Long createAnOrder(Long clientId);
    OrderEntity getOrderById(Long orderId);

    List<OrderEntity> getOrderSByClientId(Long clientId);

}
