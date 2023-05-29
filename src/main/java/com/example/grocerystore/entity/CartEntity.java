package com.example.grocerystore.entity;

import com.example.grocerystore.model.Client;
import com.example.grocerystore.model.Inventory;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name="cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    ClientEntity clientEntity;

    @OneToOne(fetch =FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name="inventory_id",referencedColumnName = "inventory_id")
    InventoryEntity inventoryEntity;
    private int units;
    private double total;
}
