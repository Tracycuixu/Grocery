package com.example.grocerystore.service;

import com.example.grocerystore.entity.CategoryEntity;
import com.example.grocerystore.model.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
    public Category save(Category category);
    public Category findById(Long id);
    public List<Category> findAll();
    public Category findByName_2(String name);
    public Category findById_2(Long id);

    }
