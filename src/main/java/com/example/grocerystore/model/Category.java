package com.example.grocerystore.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Category {
    private Long categoryId;
    private String name;
    ArrayList<Product> productList;


}