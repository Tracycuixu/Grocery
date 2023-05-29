package com.example.grocerystore.mapper;

import com.example.grocerystore.entity.*;
import com.example.grocerystore.entity.UserEntity;

import com.example.grocerystore.model.Admin;
import com.example.grocerystore.model.Category;
import com.example.grocerystore.model.Inventory;
import com.example.grocerystore.model.Product;
import com.example.grocerystore.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperHelper {

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
//        System.out.println("categories->" + categories);
        return categories;
    }
    public CategoryEntity MapperCategoryToCategoryEntity(Category category){
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
        return categoryEntity;
    }

    public Category MapperCategoryEntityToCategory(CategoryEntity categoryEntity){
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
        return category;
    }

    private final ObjectMapper mapper;

    @Autowired
    public MapperHelper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    public List<User> convertUserEntityListToUserList(List<UserEntity> entities){
        List<User> users = new ArrayList<>();
        for(UserEntity entity: entities){
            users.add(mapper.convertValue(entity, User.class));
        }
        return users;
    }
    public UserEntity convertUserToUserEntity(User user){
        return mapper.convertValue(user, UserEntity.class);
    }
    public User convertUserEntityToUser(UserEntity userEntity){
        return mapper.convertValue(userEntity, User.class);
    }

    public List<Admin> convertAdminEntityListToAdminList(List<AdminEntity> adminEntities) {
        List<Admin> admins = new ArrayList<>();
        for(AdminEntity entity: adminEntities){
            admins.add(mapper.convertValue(entity, Admin.class));
        }
        return admins;
    }
    public AdminEntity convertAdminToAdminEntity(Admin admin) {
        return mapper.convertValue(admin, AdminEntity.class);
    }

    public Admin convertAdminEntityToAdmin(AdminEntity adminEntity) {
        return mapper.convertValue(adminEntity,Admin.class);
    }



        public List<Inventory> ConvertInventoryEntitiesToInventories(List<InventoryEntity> inventoryEntities){
        List<Inventory> newInventoryList =new ArrayList<>();
        for (InventoryEntity inventoryEntity : inventoryEntities) {
            ProductEntity productEntity = inventoryEntity.getProductEntity();
            Product product= Product.builder()
                    .productId(productEntity.getProductId())
                    .unitFormat(productEntity.getUnitFormat())
                    .name(productEntity.getName()).category(MapperCategoryEntityToCategory(productEntity.getCategoryEntity())).
                    build();
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

    public List<InventoryEntity> ConvertInventoriesToInventoryEntities(List<Inventory> Inventories){
        List<InventoryEntity> InventoryEntities =new ArrayList<>();
        for (Inventory inventory : Inventories) {
            Product product = inventory.getProduct();
            ProductEntity productEntity= ProductEntity.builder()
                    .productId(product.getProductId())
                    .unitFormat(product.getUnitFormat())
                    .name(product.getName()).categoryEntity(MapperCategoryToCategoryEntity(product.getCategory())).
                    build();
            InventoryEntity inventoryEntity = InventoryEntity.builder()
                    .inventoryId(inventory.getInventoryId())
                    .costPrice(inventory.getCostPrice())
                    .quantity(inventory.getQuantity())
                    .salePrice(inventory.getSalePrice())
                    .quantity(inventory.getQuantity())
                    .status(inventory.getStatus())
                    .createdDate((inventory.getCreatedDate()))
                    .productEntity(productEntity).build();
            InventoryEntities.add(inventoryEntity);
        }
        return InventoryEntities;
    }

    public Inventory convertInventoryEntityToInventory(InventoryEntity inventoryEntity){
        Inventory inventory = Inventory.builder()
                .inventoryId(inventoryEntity.getInventoryId())
                .costPrice(inventoryEntity.getCostPrice())
                .salePrice(inventoryEntity.getSalePrice())
                .quantity(inventoryEntity.getQuantity())
                .status(inventoryEntity.getStatus())
                .product(MapperProductEntityToProductMapper(inventoryEntity.getProductEntity()))
                .build();
        return inventory;
    }


    public ProductEntity convertProductToProductEntity(Product product){
        ProductEntity productEntity = ProductEntity.builder()
                .productId(product.getProductId())
                .unitFormat(product.getUnitFormat())
                .name(product.getName())
                .imgPath(product.getImgPath())
                .categoryEntity(MapperCategoryToCategoryEntity(product.getCategory()))
                .build();
        return productEntity;
    }
    public InventoryEntity convertInventoryToInventoryEntity(Inventory inventory){
        if(inventory!=null){


        InventoryEntity inventoryEntity = InventoryEntity.builder()
                .inventoryId(inventory.getInventoryId())
                .costPrice(inventory.getCostPrice())
                .salePrice(inventory.getSalePrice())
                .quantity(inventory.getQuantity())
                .status(inventory.getStatus())
                .productEntity(convertProductToProductEntity(inventory.getProduct()))
                .build();
        return inventoryEntity;
        } else return null;
    }
    public Client convertClientEntityToClient(ClientEntity clientEntity){
        if(clientEntity!=null){
        List<CartEntity> cartEntities = clientEntity.getCartEntities();
        ArrayList<Cart> cartRecords=new ArrayList<>();
        for(CartEntity cartEntity: cartEntities){
            Cart cart=Cart.builder()
                    .units(cartEntity.getUnits())
                    .id(cartEntity.getId())
                    .total(cartEntity.getTotal())
                    .inventory(convertInventoryEntityToInventory(cartEntity.getInventoryEntity()))
                    .build();
            cartRecords.add(cart);
        }
        Client client = Client.builder()
                .clientId(clientEntity.getClientId())
                .address(clientEntity.getAddress())
                .phone(clientEntity.getPhone())
                .email(clientEntity.getEmail())
                .password(clientEntity.getPassword())
                .cartList(cartRecords)
        .build();
        return client;
        }else return null;
    }

    public ClientEntity convertClientToClientEntity(Client client){
        if(client!=null){
            ClientEntity clientEntity = ClientEntity.builder()
                    .clientId(client.getClientId())
                    .address(client.getAddress())
                    .phone(client.getPhone())
                    .email(client.getEmail())
                    .password(client.getPassword()).
                    build();
            return clientEntity;
        }else return null;

    }

    public Cart convertCartEntityToCart(CartEntity cartEntity){
        Cart cart = Cart.builder()
                .id(cartEntity.getId())
                .units(cartEntity.getUnits())
                .total(cartEntity.getTotal())
                .client(convertClientEntityToClient(cartEntity.getClientEntity()))
                .inventory(convertInventoryEntityToInventory(cartEntity.getInventoryEntity()))
        .build();
        return  cart;
    }
    public CartEntity convertCartToCartEntity(Cart cart){
        CartEntity cartEntity = CartEntity.builder()
                .id(cart.getId())
                .units(cart.getUnits())
                .total(cart.getTotal())
                .clientEntity(convertClientToClientEntity(cart.getClient()))
                .inventoryEntity(convertInventoryToInventoryEntity(cart.getInventory()))
                .build();
        return  cartEntity;
    }

}
