package com.example.grocerystore.controller;

import com.example.grocerystore.model.Inventory;
import com.example.grocerystore.model.Product;
import com.example.grocerystore.service.InventoryService;
import com.example.grocerystore.service.ProductService;
import com.example.grocerystore.view.InventoryView;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class InventoryController {
    private final InventoryService inventoryService;
    private final ProductService productService;

    @Autowired
    public InventoryController(InventoryService inventoryService, ProductService productService) {
        this.inventoryService = inventoryService;
        this.productService = productService;
    }

    @PostMapping("/addsingleInventory")
    public String addSingleInventory(@ModelAttribute InventoryView inventoryView) {
        String productId = inventoryView.getProductId();
        Product productById = productService.findById(Long.valueOf(productId));
        Inventory inventory = Inventory.builder()
                .createdDate(new Date())
                .quantity(inventoryView.getQuantity())
                .costPrice(inventoryView.getCostPrice())
                .product(productById)
                .build();
        inventoryService.save(inventory);
        return "redirect:/listallinventories";
    }

    @GetMapping("/addinventorylist")
    public String addinventorylist(@ModelAttribute InventoryView inventoryView) {
        String productId = inventoryView.getProductId();
        Product productById = productService.findById(Long.valueOf(productId));
        Inventory inventory = Inventory.builder()
                .createdDate(new Date())
                .quantity(inventoryView.getQuantity())
                .costPrice(inventoryView.getCostPrice())
                .product(productById)
                .build();
        return "redirect:/listallinventories";
    }


    @GetMapping("/updateAInventoryForm")
    public String update_addAProductionForm(@RequestParam("inventoryId") Long inventoryId, Model model) {
        Inventory byId = inventoryService.findById(inventoryId);
        model.addAttribute("inventory", byId);
        return "addAInventoryForm";

    }

    @GetMapping("/listallinventories")
    public String listAllInventories(Model model, HttpSession session) {
        List<Inventory> inventoryList = this.inventoryService.findAll();
        model.addAttribute("inventoryList", inventoryList);
        Object loggedInAdmin = session.getAttribute("loggedInAdmin");
        model.addAttribute("loggedInAdmin", loggedInAdmin);
        return "listallinventories";
    }

    @GetMapping("/deletesingleinventory")
    public String listAllInventory(Long inventoryId, Model model) {
        inventoryService.deleteById(inventoryId);
        List<Inventory> all = inventoryService.findAll();
        model.addAttribute("inventorylist", all);
        return "redirect:listallinventories";
    }


    @PostMapping("deleteselectedinventories")
    public String deleteselectedinventoryions
            (@RequestParam(name = "checkbox_delete", required = false) List<String> check_deleted) {
        for (String idOfInventory : check_deleted) {
            inventoryService.deleteById(Long.valueOf(idOfInventory));
        }
        return "redirect:/listallinventories";
    }
}
