package com.example.grocerystore.service;

import com.example.grocerystore.entity.OrderDetailEntity;
import com.example.grocerystore.entity.OrderEntity;
import com.example.grocerystore.mapper.MapperHelper;
import com.example.grocerystore.model.Cart;
import com.example.grocerystore.model.Order;
import com.example.grocerystore.model.OrderDetail;
import com.example.grocerystore.repository.OrderDetailRepo;
import com.example.grocerystore.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    private final OrderRepo orderRepo;
    private final OrderDetailRepo orderDetailRepo;
    private final CartService cartService;
    private final MapperHelper mapperHelper;
    public OrderDetailServiceImpl(OrderRepo orderRepo, OrderDetailRepo orderDetailRepo, CartService cartService, MapperHelper mapperHelper) {
        this.orderRepo = orderRepo;
        this.orderDetailRepo = orderDetailRepo;
        this.cartService = cartService;
        this.mapperHelper = mapperHelper;
    }
    @Override
    public void addToOrderList(Long orderId) {
        try {
            Optional<OrderEntity> order=orderRepo.findById(orderId);
                Double amount = 0.00;
                List<Cart> AllCartRecordByClient = cartService.getAllCartRecordsByClientId(order.get().getClientEntity().getClientId());
                for (Cart cart : AllCartRecordByClient) {
                    OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                    orderDetailEntity.setOrderEntity(order.get());
                    orderDetailEntity.setInventoryEntity(mapperHelper.convertInventoryToInventoryEntity(cart.getInventory()));
                    orderDetailEntity.setUnits(cart.getUnits());
                    orderDetailEntity.setTotal(cart.getTotal());
                    orderDetailRepo.save(orderDetailEntity);
                    amount += cart.getTotal();
                }
                order.get().setAmount(Math.round(amount * 100.0) / 100.0);
                orderRepo.save(order.get());
                cartService.removeAllCartRecord(order.get().getClientEntity().getClientId());
        } catch (Exception e){
            System.out.println("addToOrderListException => " + e.getMessage());
        }
    }
    @Override
    public List<OrderDetailEntity> getOrderDetails(Long orderId){
        List<OrderDetailEntity> allOrderDetailRecords=orderDetailRepo.findAll();
        List<OrderDetailEntity> foundOrderDetails=new ArrayList<>();

        for(OrderDetailEntity orderDetailEntity: allOrderDetailRecords){
            if(orderDetailEntity.getOrderEntity().getOrderId().equals(orderId)){
                foundOrderDetails.add(orderDetailEntity);
            }
        }
        return foundOrderDetails;
    }
}
