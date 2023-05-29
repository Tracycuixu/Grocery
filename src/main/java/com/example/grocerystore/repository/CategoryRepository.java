package com.example.grocerystore.repository;

import com.example.grocerystore.entity.CategoryEntity;
import com.example.grocerystore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    Optional<CategoryEntity> findByName(String name);
}
