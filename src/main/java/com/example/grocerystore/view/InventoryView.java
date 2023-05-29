package com.example.grocerystore.view;

import com.example.grocerystore.entity.ProductEntity;
import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class InventoryView {
    private Long inventoryId;
    private Long categoryId;
    private String productId;
    private Double costPrice;
    private Double salePrice;
    private Integer quantity;
    private String status;
    private Date createdDate;
}
