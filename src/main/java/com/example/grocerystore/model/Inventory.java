package com.example.grocerystore.model;

import com.example.grocerystore.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class Inventory {
    private Long inventoryId;
    private Product product;
    private Double costPrice;
    private Double salePrice;
    private Integer quantity;
    private String status;
    private Date createdDate;
}
