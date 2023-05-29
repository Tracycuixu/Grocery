package com.example.grocerystore.model;

import lombok.*;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class OrderDetail {
    private Long orderRecordId;
    Order order;
    Inventory inventory;

    private int units;

    private double total;
}
