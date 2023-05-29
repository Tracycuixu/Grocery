package com.example.grocerystore.view;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class productionView {
       Long id;
       Long prodId;
       String name;
       String description;
       Double unitPrice;
       Integer units;
       Double totalPrice;
       String productCategoryName;
}
