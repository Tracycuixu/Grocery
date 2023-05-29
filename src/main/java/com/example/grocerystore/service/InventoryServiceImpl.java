package com.example.grocerystore.service;

import com.example.grocerystore.entity.ProductEntity;
import com.example.grocerystore.entity.InventoryEntity;
import com.example.grocerystore.mapper.MapperHelper;

import com.example.grocerystore.model.Product;
import com.example.grocerystore.model.Inventory;
import com.example.grocerystore.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final MapperHelper mapperHelper;
    private final InventoryRepository inventoryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ObjectMapper objectMapper, MapperHelper mapperHelper, ObjectMapper objectMapper1) {
        this.inventoryRepository = inventoryRepository;
        this.mapperHelper = mapperHelper;
        this.objectMapper = objectMapper1;
    }

    @Override
    public List<Inventory> findAll() {
        List<InventoryEntity> all1 = inventoryRepository.findAll();
        List<Inventory> newInventoryList = new ArrayList<>();
        for (InventoryEntity inventoryEntity : all1) {
            ProductEntity productEntity = inventoryEntity.getProductEntity();
            Product product = Product.builder()
                    .productId(productEntity.getProductId())
                    .unitFormat(productEntity.getUnitFormat())
                    .name(productEntity.getName())
                    .category(mapperHelper.MapperCategoryEntityToCategory(productEntity.getCategoryEntity()))
                    .build();
            Inventory inventory = Inventory.builder()
                    .inventoryId(inventoryEntity.getInventoryId())
                    .costPrice(inventoryEntity.getCostPrice())
                    .quantity(inventoryEntity.getQuantity())
                    .salePrice(inventoryEntity.getSalePrice())
                    .quantity(inventoryEntity.getQuantity())
                    .status(inventoryEntity.getStatus())
                    .createdDate((inventoryEntity.getCreatedDate()))
                    .product(product).build();
            newInventoryList.add(inventory);
        }
        return newInventoryList;
    }

    @Override
    public Inventory findById(Long inventoryId) {
        Optional<InventoryEntity> byId = inventoryRepository.findById(inventoryId);
        InventoryEntity inventory = byId.get();
        Inventory inventory1 = objectMapper.convertValue(inventory, Inventory.class);
        return inventory1;

    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);

    }

    @Override
    public Inventory save(Inventory inventory) {
        System.out.println("InventoryionRepository.inventoryion->" + inventory);
        InventoryEntity inventoryEntity = objectMapper.convertValue(inventory, InventoryEntity.class);
        System.out.println("InventoryionRepository.inventoryionEntity->" + inventoryEntity);
        Product product = inventory.getProduct();
        ProductEntity productEntity = objectMapper.convertValue(product, ProductEntity.class);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("InventoryionRepository.productEntity->" + productEntity);
        inventoryEntity.setProductEntity(productEntity);
        System.out.println("InventoryionRepository.finalinventoryionEntityave->" + inventoryEntity);

        System.out.println("InventoryionRepository.finaleProductEntityy->" + productEntity);
        inventoryRepository.save(inventoryEntity);
        return inventory;
    }

    @Override
    public Inventory findInventoryById(Long inventoryId) {
        List<Inventory> AllProductInventories = this.findAll();
        Inventory foundInventory = new Inventory();
        for (Inventory inventory : AllProductInventories) {
            if (inventory.getInventoryId().equals(inventoryId)) {
                foundInventory = inventory;
            }
        }
        return foundInventory;

    }

    @Override
    public List<Inventory> findByCategory(String categoryName) {
        List<Inventory> AllProductInventories = this.findAll();
        List<Inventory> inventoriesByCategory = new ArrayList<>();
        for (Inventory inventory : AllProductInventories) {
            if (inventory.getProduct().getCategory().getName().equals(categoryName)) {
                inventoriesByCategory.add(inventory);
            }
        }
        return inventoriesByCategory;
    }

    @Override
    public List<Inventory> findByStatus(String status) {
        List<Inventory> AllProductInventories = this.findAll();
        List<Inventory> inventoriesByCategory = new ArrayList<>();
        for (Inventory inventory : AllProductInventories) {
            if (inventory.getStatus() != null) {
                if (inventory.getStatus().equals(status)) {
                    inventoriesByCategory.add(inventory);
                }

            }
        }
        return inventoriesByCategory;
    }

    @Override
    public List<Inventory> findInventoriesByProductName(String productName){
        List<Inventory> inventoryEntities = findAll();
        List<Inventory> inventoryListByProductName=new ArrayList<>();
        for(Inventory inventory: inventoryEntities){
            if(inventory.getProduct().getName().contains(productName)){
                inventoryListByProductName.add(inventory);
            }
        } return inventoryListByProductName;
    }
}