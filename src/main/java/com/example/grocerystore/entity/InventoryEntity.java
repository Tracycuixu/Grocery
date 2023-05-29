package com.example.grocerystore.entity;

import com.example.grocerystore.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table (name="inventory")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class InventoryEntity {
    @Id
    @Column(name="inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

//    @OneToOne(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn(name="product_id",referencedColumnName = "product_id")
    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="product_id")
    private ProductEntity productEntity;
    @Column(name="buy_price")
    private Double costPrice;
    @Column(name="sale_price")
    private Double salePrice;
    @Column(name="quantity")
    private Integer quantity;
    @Column(name="status")
    private String status;
    @Column(name="created_date")
    private Date createdDate;
}
