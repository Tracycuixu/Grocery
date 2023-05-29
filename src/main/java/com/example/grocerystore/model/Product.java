package com.example.grocerystore.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class Product {
    Long productId;
    String name;
    String unitFormat;
    String imgPath;
    Category category;
    List<Inventory> inventories;

    @Override
    public String toString() {
        return "Production{" +
                "Id=" + productId +
                ", name='" + name + '\'' +
                ", unitFormat='" + unitFormat + '\'' +
                ", imgPath='" + imgPath + '\'' +
                //          ", category=" + category +
                '}';
    }
}

