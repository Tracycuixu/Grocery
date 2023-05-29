package com.example.grocerystore.service;

import com.example.grocerystore.entity.OrderEntity;
import com.example.grocerystore.mapper.MapperHelper;
import com.example.grocerystore.model.Client;
import com.example.grocerystore.model.Order;
import com.example.grocerystore.repository.ClientRepo;
import com.example.grocerystore.repository.OrderRepo;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepo orderRepo;
    private final MapperHelper mapperHelper;
    private final ClientRepo clientRepo;
    private final ClientService clientService;

    public OrderServiceImpl(OrderRepo orderRepo, MapperHelper mapperHelper, ClientRepo clientRepo, ClientService clientService) {
        this.orderRepo = orderRepo;
        this.mapperHelper = mapperHelper;
        this.clientRepo = clientRepo;
        this.clientService = clientService;
    }


    @Override
    public Long createAnOrder(Long clientId) {
//        List<OrderEntity> AllOrderEntities= orderRepo.findAll();
        Client client=clientService.getClientById(clientId);
        Long orderId=orderRepo.count()+1;
        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setOrderId(orderId);
        orderEntity.setClientEntity(mapperHelper.convertClientToClientEntity(client));
        orderRepo.save(orderEntity);
        return orderId;
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        Optional<OrderEntity> order = orderRepo.findById(orderId);
        return order.get();
    }
    @Override
    public List<OrderEntity> getOrderSByClientId(Long clientId){
        List<OrderEntity> orderList = orderRepo.findAll();
        List<OrderEntity> orderListByClient =new ArrayList<>();
        for(OrderEntity orderEntity: orderList){
            if(orderEntity.getClientEntity().getClientId().equals(clientId)){
                orderListByClient.add(orderEntity);
            }
        }
        return orderListByClient;
    }
}
