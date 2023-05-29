package com.example.grocerystore.entity;

import com.example.grocerystore.model.Inventory;
import com.example.grocerystore.model.Order;
import jakarta.persistence.*;
import lombok.*;

@Table(name="OrderDetail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class OrderDetailEntity {

    @Id
    @Column(name="orderRecord_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    OrderEntity orderEntity;

    @OneToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="inventory_id",referencedColumnName = "inventory_id")
    InventoryEntity inventoryEntity;

    private int units;

    private double total;
}
