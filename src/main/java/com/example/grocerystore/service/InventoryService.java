package com.example.grocerystore.service;

import com.example.grocerystore.model.Inventory;
import com.example.grocerystore.model.Product;

import java.util.List;

public interface InventoryService {
    public Inventory save(Inventory inventory);
    public Inventory findById(Long id);
    public List<Inventory> findAll();
    public void deleteById(Long id);

    List<Inventory> findByCategory(String categoryName);
    List<Inventory> findByStatus(String status);
    Inventory findInventoryById(Long inventoryId);
    List<Inventory> findInventoriesByProductName(String productName);
    }

