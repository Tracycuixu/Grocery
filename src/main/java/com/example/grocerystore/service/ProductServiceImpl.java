package com.example.grocerystore.service;

import com.example.grocerystore.entity.CategoryEntity;
import com.example.grocerystore.entity.ProductEntity;
import com.example.grocerystore.model.Category;
import com.example.grocerystore.model.Product;
import com.example.grocerystore.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ObjectMapper objectMapper, ObjectMapper objectMapper1) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper1;
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> all1 = productRepository.findAll();
        List<Product> newProductList = new ArrayList<>();
        for (ProductEntity productEntity : all1) {
            CategoryEntity categoryEntity = productEntity.getCategoryEntity();
            Category category = Category.builder()
                    .categoryId(categoryEntity.getCategoryId())
                    .name(categoryEntity.getName()).build();
            Product product = com.example.grocerystore.model.Product.builder()
                    .productId(productEntity.getProductId())
                    .name(productEntity.getName())
                    .unitFormat(productEntity.getUnitFormat())
                    .imgPath(productEntity.getImgPath())
                    .category(category).build();
            newProductList.add(product);
        }
        return newProductList;
    }

    @Override
    public Product findById(Long productId) {
        Optional<ProductEntity> byId = productRepository.findById(productId);
        ProductEntity productEntity = byId.get();
        Product product1 = MapperProductEntityToProductMapper(productEntity);
        return product1;

    }

    @Override
    public void deleteById(Integer id) {
        productRepository.deleteById(id.longValue());

    }

    @Override
    public void save(Product product) {
        ProductEntity productEntity = objectMapper.convertValue(product, ProductEntity.class);
        Category category = product.getCategory();
        CategoryEntity categoryEntity = objectMapper.convertValue(category, CategoryEntity.class);
        productEntity.setCategoryEntity(categoryEntity);
        productRepository.save(productEntity);
    }

    public Product MapperProductEntityToProductMapper(ProductEntity productEntity) {
        CategoryEntity categoryEntity = productEntity.getCategoryEntity();
        Category category = Category.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName()).build();

        Product product = Product.builder()
                .name(productEntity.getName())
                .unitFormat(productEntity.getUnitFormat())
                .imgPath(productEntity.getImgPath())
                .productId(productEntity.getProductId())
                .category(category)
                .build();
        return product;
    }
}
