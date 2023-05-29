package com.example.grocerystore.service;

import com.example.grocerystore.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> findAll();
    public Product findById(Long productId);
    public void deleteById(Integer id);
    public void save(Product product);
}
