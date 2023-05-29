package com.example.grocerystore.service;

import com.example.grocerystore.entity.OrderDetailEntity;
import com.example.grocerystore.model.Cart;
import com.example.grocerystore.model.Order;

import java.util.List;

public interface OrderDetailService {

    void addToOrderList(Long orderId);
    List<OrderDetailEntity> getOrderDetails(Long orderId);
}
