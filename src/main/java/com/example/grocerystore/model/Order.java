package com.example.grocerystore.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Order {
    private Long id;
    private Long orderId;
    Client client;

    double amount;

    private String deliveryAddress;

    Boolean paid;

    private Date Created_at;

    List<OrderDetail> orderDetailList;

}
