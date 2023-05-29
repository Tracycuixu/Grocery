package com.example.grocerystore.repository;

import com.example.grocerystore.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<CartEntity,Long> {
}
