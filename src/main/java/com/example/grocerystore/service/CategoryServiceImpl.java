package com.example.grocerystore.service;

import com.example.grocerystore.entity.CategoryEntity;
import com.example.grocerystore.entity.ProductEntity;
import com.example.grocerystore.model.Category;
import com.example.grocerystore.model.Product;
import com.example.grocerystore.repository.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    public CategoryRepository categoryRepository;
    public ObjectMapper objectMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ObjectMapper objectMapper){
        this.categoryRepository =categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = objectMapper.convertValue(category, CategoryEntity.class);
        this.categoryRepository.save(categoryEntity);
        return category;
    }

    @Override
    public Category findById_2(Long id) {
        Optional<CategoryEntity> categoryEntityById = categoryRepository.findById(id);
        CategoryEntity categoryEntity = categoryEntityById.get();
//        System.out.println("categoryServiceImpl->findById->"+categoryEntity);
        Category category = MapperCategoryEntityToCategory_2(categoryEntity);
//        System.out.println("categoryServiceImpl->findById->"+category);
        return category;
    }
    @Override
    public Category findById(Long id) {
        Optional<CategoryEntity> categoryEntityById = categoryRepository.findById(id);
        CategoryEntity categoryEntity = categoryEntityById.get();
//        System.out.println("categoryServiceImpl->findById->"+categoryEntity);
        Category category = MapperCategoryEntityToCategory(categoryEntity);
//        System.out.println("categoryServiceImpl->findById->"+category);
        return category;
    }

    @Override
    public Category findByName_2(String name) {
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findByName(name);
        CategoryEntity categoryEntity = optionalCategoryEntity.get();
//        System.out.println("************=>"+categoryEntity);
        Category category= MapperCategoryEntityToCategory_2(categoryEntity);
//        System.out.println("************=>"+category);

        return category;
    }

    public List<Category> MapperListCategoryEntityListToCategory(List<CategoryEntity> categoryEntityList) {
//        System.out.println("allllll-----" + categoryEntityList);
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            List<ProductEntity> productEntityList = categoryEntity.getProductEntityList();
            ArrayList<Product> productList=new ArrayList<>();
            for (ProductEntity productEntity : productEntityList) {
                Product product = Product.builder()
                        .productId(productEntity.getProductId())
                        .name(productEntity.getName())
                        .imgPath(productEntity.getImgPath())
                        .unitFormat(productEntity.getUnitFormat())

                        .build();
                productList.add(product);
            }


            Category category = Category.builder()
                    .categoryId(categoryEntity.getCategoryId())
                    .name(categoryEntity.getName())
                    .productList(productList)
                    .build();
            categories.add(category);
        }
        System.out.println("categories->" + categories);
        return categories;
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> all = categoryRepository.findAll();
        List<Category> categories = MapperListCategoryEntityListToCategory(all);
        System.out.println("categories->"+categories);
        return categories;
    }



    public CategoryEntity MapperCategoryToCategoryEntity(Category category){
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
        return categoryEntity;
    }
    public Category MapperCategoryEntityToCategory_2(CategoryEntity categoryEntity){
        Category category = Category.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .build();
        return category;
    }
    public Category MapperCategoryEntityToCategory(CategoryEntity categoryEntity){
        System.out.println("111111111111111"+categoryEntity);
        List<ProductEntity> productEntityList = categoryEntity.getProductEntityList();
        System.out.println("2222222222222222"+productEntityList);
        ArrayList<Product> productList=new ArrayList<>();
        if (productEntityList.isEmpty()){

        }
        else {
            for (ProductEntity productEntity : productEntityList) {
                Product product = Product.builder()
                        .productId(productEntity.getProductId())
                        .name(productEntity.getName())
                        .imgPath(productEntity.getImgPath())
                        .unitFormat(productEntity.getUnitFormat())
                        .build();
                productList.add(product);
            }
        }

        Category category = Category.builder()
                .categoryId(categoryEntity.getCategoryId())
                .name(categoryEntity.getName())
                .productList(productList)
                .build();
        System.out.println("4444444444444444444"+category);
        return category;
    }
}
