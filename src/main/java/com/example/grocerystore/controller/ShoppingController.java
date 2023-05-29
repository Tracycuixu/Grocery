package com.example.grocerystore.controller;

import com.example.grocerystore.entity.OrderDetailEntity;
import com.example.grocerystore.entity.OrderEntity;
import com.example.grocerystore.model.*;
import com.example.grocerystore.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShoppingController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final InventoryService inventoryService;
    private final CartService cartService;
    private final ClientService clientService;
    private  final OrderService orderService;
    private  final OrderDetailService orderDetailService;
    private  final UserService userService;
    @Autowired
    public ShoppingController(ProductService productService, CategoryService categoryService, InventoryService inventoryService, CartService cartService, ClientService clientService, OrderService orderService, OrderDetailService orderDetailService, UserService userService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
        this.cartService = cartService;
        this.clientService = clientService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String shoppingHomePage(Model model) {
        Long clientId=1L;
        model.addAttribute("clientId", clientId);
        List<Product> allProducts = this.productService.findAll();
        List<Category> categories = this.categoryService.findAll();
        List<Inventory> inventories =this.inventoryService.findAll();
//        System.out.println(productList);
        model.addAttribute("allProducts", allProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("inventories", inventories);
        model.addAttribute("cart", new Cart());
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        return "index_c";
    }

    @GetMapping("/products")
    public  String showProductsPage(Model model, HttpSession session){

        Long clientId=1L;
        model.addAttribute("clientId", clientId);
        List<Inventory> productsOnSale =this.inventoryService.findByStatus("sale");
        List<Inventory> newProducts =this.inventoryService.findByStatus("new");
        List<Inventory> inventories =this.inventoryService.findAll();
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("productsOnSale", productsOnSale);
        model.addAttribute("newProducts", newProducts);
        model.addAttribute("inventories", inventories);
        model.addAttribute("cart", new Cart());
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        return "shopping/products";
    }

    @GetMapping("/products/category")
    public String shoppingVegetables(@RequestParam("cate") String cate, Model model){
        Long clientId=1L;
        model.addAttribute("clientId", clientId);
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        List<Inventory> Inventories = inventoryService.findByCategory(cate);
        model.addAttribute("inventories", Inventories);
        List<Inventory> productsOnSale =this.inventoryService.findByStatus("sale");
        List<Inventory> newProducts =this.inventoryService.findByStatus("new");
        model.addAttribute("productsOnSale", productsOnSale);
        model.addAttribute("newProducts", newProducts);
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        model.addAttribute("cart", new Cart());
        return "shopping/products";

    }

    @PostMapping("/addToCart")
    public String addToCart2 (@ModelAttribute Cart cart, String inventoryId,String clientNumber,String units,RedirectAttributes attributes){
        Long InventId = Long.valueOf(inventoryId);
        Long clientId= Long.valueOf(clientNumber);
        int unitsInt= Integer.valueOf(units);
        Inventory inventory=inventoryService.findInventoryById(InventId);
        Client client = clientService.getClientById(clientId);
        cartService.addToCart(cart,client,inventory,unitsInt);
        attributes.addFlashAttribute("id", clientId);
        attributes.addAttribute("id", clientId);
        return "redirect:/shoppingCart";
    }

    @PostMapping("/updateCart")
    public String updateCart (@ModelAttribute Cart cart, String cartId,String clientNumber,String units,RedirectAttributes attributes){
        Long cartIdL = Long.valueOf(cartId);
        Long clientId= Long.valueOf(clientNumber);
        int unitsInt= Integer.valueOf(units);
        Client client = clientService.getClientById(clientId);
        cartService.updateCart(cartIdL,unitsInt);
        attributes.addFlashAttribute("id", clientId);
        attributes.addAttribute("id", clientId);
        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCart(@RequestParam("id")Long clientId,Model model){
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        List<Cart> cartRecords = cartService.getAllCartRecordsByClientId(clientId);
        model.addAttribute("cartRecords",cartRecords);
        model.addAttribute("cart", new Cart());
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        model.addAttribute("clientId",clientId);

        return "shopping/shopping-cart";
    }

    @GetMapping("/delete")
    public String deleteByCartRecord(@RequestParam("id") Long cartRecordId, RedirectAttributes attributes){
        Long clientId=1L;
//        model.addAttribute("clientId", clientId);
        cartService.removeCartRecordByCartRecordId(cartRecordId);
        attributes.addFlashAttribute("id", clientId);
        attributes.addAttribute("id", clientId);
        return "redirect:/shoppingCart";
}

    @GetMapping("/search")
    public String searchInventory(String productNameToSearch,Model model,RedirectAttributes attributes){
        Long clientId=1L;
        model.addAttribute("clientId", clientId);
        List<Inventory> foundInventories = inventoryService.findInventoriesByProductName(productNameToSearch);
        if(foundInventories.size()>0) {
            List<Category> categories = this.categoryService.findAll();
            model.addAttribute("categories", categories);
            List<Inventory> Inventories = inventoryService.findByCategory(foundInventories.get(0).getProduct().getCategory().getName());
            model.addAttribute("inventories", Inventories);
            model.addAttribute("foundInventories", foundInventories);
            model.addAttribute("cart", new Cart());
            return "shopping/product-details2";
        }
      List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        model.addAttribute("message", "The product is coming soon!");
        return "shopping/error";
    }

    @GetMapping("/productDetail")
    public  String showProductsDetail(@RequestParam("id") Long theId, Model model){
        Long clientId=1L;
        model.addAttribute("clientId", clientId);
        List<Category> categories = this.categoryService.findAll();
        model.addAttribute("categories", categories);
        Inventory inventory =this.inventoryService.findInventoryById(theId);
        model.addAttribute("inventory", inventory);
        Cart cartRecord = this.cartService.getCartRecordByInventoryId(clientId,theId);
        model.addAttribute("cartRecord", cartRecord);
        List<Inventory> Inventories = inventoryService.findByCategory(inventory.getProduct().getCategory().getName());
        model.addAttribute("inventories", Inventories);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        model.addAttribute("cart", new Cart());
        return "shopping/product-details";
    }
    @GetMapping("/checkout")
    public  String checkOut(Model model,RedirectAttributes attributes){
        Long clientId=1L;
        model.addAttribute("clientId", clientId);
        double cartAmount = this.cartService.getCartAmount(clientId);
        model.addAttribute("cartAmount", cartAmount);
        Integer cartListCount = this.cartService.getCartListCount(clientId);
        model.addAttribute("cartListCount", cartListCount);
        User client= userService.getUserById(clientId);
        model.addAttribute("client",client);
        Long orderId=orderService.createAnOrder(clientId);
        orderDetailService.addToOrderList(orderId);
        model.addAttribute("orderId",orderId);
        OrderEntity order=orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        List<OrderDetailEntity> orderDetailRecords=orderDetailService.getOrderDetails(orderId);
        model.addAttribute("orderDetailRecords", orderDetailRecords);
        return "shopping/checkout";
   }

   @GetMapping("/success")
    public String successPage(Model model){
       Long clientId=1L;
       model.addAttribute("clientId", clientId);
       double cartAmount = this.cartService.getCartAmount(clientId);
       model.addAttribute("cartAmount", cartAmount);
       Integer cartListCount = this.cartService.getCartListCount(clientId);
       model.addAttribute("cartListCount", cartListCount);
       model.addAttribute("message", "Your order has been successfully placed. Thank you for shopping with us!");
       return "shopping/success";
   }
}
