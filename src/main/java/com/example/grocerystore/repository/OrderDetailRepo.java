package com.example.grocerystore.repository;

import com.example.grocerystore.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetailEntity,Long> {
}
