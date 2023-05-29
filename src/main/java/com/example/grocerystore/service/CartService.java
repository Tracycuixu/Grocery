package com.example.grocerystore.service;

import com.example.grocerystore.model.Cart;
import com.example.grocerystore.model.Client;
import com.example.grocerystore.model.Inventory;

import java.util.List;

public interface CartService {

    public void addToCart(Cart cart, Client client, Inventory inventory,int units);
    public List<Cart> getAllCartRecordsByClientId(Long clientId);
    public Cart getCartRecordByInventoryId(Long clientId,Long inventoryId);
    public Cart getCartRecordByCartRecordId(Long CartRecordId);
    public void removeAllCartRecord(Long clientId);

    public void removeCartRecordByCartRecordId(Long CartRecordId);
    public void removeCartRecordByInventoryId(Long inventoryId);
    Integer getCartListCount(Long clientId);
    double getCartAmount(Long clientId);

    public void updateCart(Long cartId,int units);
}
