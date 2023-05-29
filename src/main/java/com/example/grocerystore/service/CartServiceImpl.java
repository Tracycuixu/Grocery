package com.example.grocerystore.service;

import com.example.grocerystore.entity.CartEntity;
import com.example.grocerystore.entity.ClientEntity;
import com.example.grocerystore.entity.InventoryEntity;
import com.example.grocerystore.mapper.MapperHelper;
import com.example.grocerystore.model.Cart;
import com.example.grocerystore.model.Client;
import com.example.grocerystore.model.Inventory;
import com.example.grocerystore.repository.CartRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;


@Service
@Transactional
public class CartServiceImpl implements CartService{

    private final CartRepo cartRepo;
    private final ClientService clientService;
    private final InventoryService inventoryService;
    private final MapperHelper mapperHelper;
    private final ObjectMapper objectMapper;

    @Autowired
    public CartServiceImpl(CartRepo cartRepo, ClientService clientService, InventoryService inventoryService, MapperHelper mapperHelper, ObjectMapper objectMapper) {
        this.cartRepo = cartRepo;
        this.clientService = clientService;
        this.inventoryService = inventoryService;
        this.mapperHelper = mapperHelper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addToCart(Cart cart,Client client,Inventory inventory,int units) {

        Cart existingCartRecord = getCartRecordByInventoryId(client.getClientId(), inventory.getInventoryId());
//        System.out.println("yang testtt"+existingCartRecord);
//        CartEntity existingCartEntity = mapperHelper.convertCartToCartEntity(existingCartRecord.orElse(null));
        if(existingCartRecord.getId()!=null){
            CartEntity existingCartEntity = mapperHelper.convertCartToCartEntity(existingCartRecord);
            units += existingCartEntity.getUnits();
            existingCartEntity.setUnits(units);
            existingCartEntity.setTotal(roundTo2Decimal(inventory.getSalePrice())* existingCartEntity.getUnits());

            cartRepo.save(existingCartEntity);
            inventory.setQuantity(inventory.getQuantity()-units);
            inventoryService.save(inventory);
        }else {
            CartEntity cartEntity = objectMapper.convertValue(cart,CartEntity.class);
        ClientEntity clientEntity = objectMapper.convertValue(client,ClientEntity.class);
        InventoryEntity inventoryEntity = objectMapper.convertValue(inventory,InventoryEntity.class);

            cartEntity.setClientEntity(clientEntity);
        cartEntity.setInventoryEntity(inventoryEntity);
        cartEntity.setUnits(units);
        cartEntity.setTotal(roundTo2Decimal(inventoryEntity.getSalePrice())*units);
//            CartEntity cartRecordEntity = mapperHelper.convertCartToCartEntity(cart);
            cartRepo.save(cartEntity);
            inventory.setQuantity(inventory.getQuantity()-units);
            inventoryService.save(inventory);
        }

    }
    @Override
    public void updateCart(Long cartId,int units){
        Cart cartReord=getCartRecordByCartRecordId(cartId);
        int unitsInCart=cartReord.getUnits();
        int unitsTotalInInventory=cartReord.getInventory().getQuantity();
        cartReord.setUnits(units);
        cartReord.setTotal(cartReord.getInventory().getSalePrice()*units);
        cartRepo.save(mapperHelper.convertCartToCartEntity(cartReord));
        cartReord.getInventory().setQuantity(unitsTotalInInventory-(units-unitsInCart));
        inventoryService.save(cartReord.getInventory());
    }

    @Override
    public List<Cart> getAllCartRecordsByClientId(Long clientId) {
        List<CartEntity> cartEntities = cartRepo.findAll();
        List<Cart> cartRecords = new ArrayList<>();
        for(CartEntity cartEntity: cartEntities){

            if(cartEntity.getClientEntity().getClientId().equals(clientId)){
                cartRecords.add(mapperHelper.convertCartEntityToCart(cartEntity));
            }
        }
        return cartRecords;
    }
    @Override
    public Integer getCartListCount(Long clientId){
        List<Cart> cartRecords = getAllCartRecordsByClientId(clientId);
        return cartRecords.size();
    }
//    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public double getCartAmount(Long clientId) {

        List<Cart> cartRecords = getAllCartRecordsByClientId(clientId);
        double amount=0;
        for(Cart cart: cartRecords){
            amount+=cart.getTotal();
        }

        return Math.round(amount * 100.0) / 100.0;
    }


    @Override
    public Cart getCartRecordByInventoryId(Long clientId,Long inventoryId) {
        List<Cart> cartRecordsOwnByClient = getAllCartRecordsByClientId(clientId);
        Cart cartRecordByInventory = new Cart();
        for(Cart cartRecord: cartRecordsOwnByClient){
            if(cartRecord.getInventory().getInventoryId().equals(inventoryId)){
                cartRecordByInventory=cartRecord;
            }

        }
       return cartRecordByInventory;
    }

    @Override
    public Cart getCartRecordByCartRecordId(Long cartRecordId) {
        Optional<CartEntity> cartEntity = cartRepo.findById(cartRecordId);
        Cart cart = mapperHelper.convertCartEntityToCart(cartEntity.orElse(null));
        return cart;
    }

    @Override
    public void removeAllCartRecord(Long clientId) {
        List<Cart> cartRecordToRemove = getAllCartRecordsByClientId(clientId);
        for(Cart cart: cartRecordToRemove){
            CartEntity cartEntity = mapperHelper.convertCartToCartEntity(cart);
            cartRepo.delete(cartEntity);
        }
    }

    @Override
    public void removeCartRecordByCartRecordId(Long cartRecordId) {
            cartRepo.deleteById(cartRecordId);
    }

    @Override
    public void removeCartRecordByInventoryId(Long inventoryId) {

    }

    private static double roundTo2Decimal(double number){
        double newNumber = Math.round(number * 100.0) / 100.0;
        return newNumber;
    }
}
