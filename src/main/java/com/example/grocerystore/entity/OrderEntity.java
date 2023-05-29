package com.example.grocerystore.entity;

import com.example.grocerystore.model.Client;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="order_id",unique = true)
    private Long orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    ClientEntity clientEntity;

    @OneToMany(mappedBy = "orderEntity",fetch = FetchType.LAZY)
    List<OrderDetailEntity> OrderDetailEntityList;
    double amount;

    private String deliveryAddress;

    Boolean paid;

    private Date Created_at;

    @OneToMany(mappedBy = "orderEntity",fetch = FetchType.LAZY)
    List<OrderDetailEntity> OrderDetailEntities;
}
