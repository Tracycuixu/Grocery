package com.example.grocerystore.controller;

import com.example.grocerystore.model.Category;
import com.example.grocerystore.model.Product;
import com.example.grocerystore.service.CategoryService;
import com.example.grocerystore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    /**
     * List All Productions, and in this list page, have delete update detail view button as well as add a new production button
     *
     * @param model
     * @return
     */
    @GetMapping("/listallproductions")
    public String listAllProductions(Model model, HttpSession session) {
        List<Product> productList = this.productService.findAll();
        model.addAttribute("productionslist", productList);
        Object loggedInAdmin = session.getAttribute("loggedInAdmin");
        model.addAttribute("loggedInAdmin", loggedInAdmin);
        return "listallproductions";
    }

    /**
     * on List All Productions page, you press delete, it will delete this production by production_id
     *
     * @param productId
     * @param model
     * @return
     */
    @GetMapping("/deletesingleproduct")
    public String listAllProductions(Integer productId, Model model) {
        productService.deleteById(productId);
        List<Product> all = productService.findAll();
        model.addAttribute("productionslist", all);
        return "listallproductions";
    }

    /**
     * Batch delete productions
     * on list all production page, you choose the productions you want to delete and choose delete selected button,
     * it will do batch delete operation
     *
     * @param check_deleted
     * @return
     */

    @PostMapping("deleteselectedproductions")
    public String deleteselectedproductions
    (@RequestParam(name = "checkbox_delete", required = false) List<String> check_deleted) {
        for (String idOfProduction : check_deleted) {
            productService.deleteById(Integer.valueOf(idOfProduction));
        }
        return "redirect:/listallproductions";
    }


    /**
     * From : list all production page
     * Previous PageAction: press the 'update' button for individual prodution.
     * CurrentPage: a filled form with previous production information
     * CurrentPage Action: modified production information and press 'submit' button
     * Result: add a new record for production and go to list all production page
     *
     * @param productionId
     * @param model
     * @return
     */
    @GetMapping("/updateAProductionForm")
    public String update_addAProductionForm(@RequestParam("productionId") Long productionId, Model model) {
        Product byId = productService.findById(productionId);
        model.addAttribute("production", byId);
        return "addAProductionForm";


    }

    /**
     * From: list all production page
     * to : add a production form
     * PageAction: on the top of list of production, press 'add new Product' button
     * result: go to a add production page, this page is thymeleaf html.
     * add on corresponding data of production will display on this thymeleaf form
     *
     * @param product
     * @param selectedValue
     * @return
     */
    @PostMapping("/addsingleproduct")
    public String addSingleProduct(@ModelAttribute Product product, String selectedValue, String categoryname, @RequestParam("file") MultipartFile file) throws IOException {
        Category category = null;
        Long id = Long.valueOf(selectedValue);
        if (id == 0) {
            category = categoryService.save(Category.builder()
                    .name(categoryname)
                    .build());
            Category byName = categoryService.findByName_2(categoryname);
            id = byName.getCategoryId();
        }

        category = categoryService.findById_2(id);

        product.setCategory(category);
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                String filePath = "static/img/product/" + fileName; // 指定文件保存路径
                Resource resource = new ClassPathResource("static/img/product/");
             if (!resource.exists()) {
                    resource.getFile().mkdirs();
                }
                File dest = new File(resource.getFile().getAbsolutePath() + "/" + fileName);
                file.transferTo(dest);
                product.setImgPath(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productService.save(product);
        return "redirect:/listallproductions";
    }

    /***
     * test for cascade secondary category and primary category
     * code is good
     * @return
     */
    @GetMapping("/addsingleproducttestforcategory")
    public String addSingleProducttestforcategory() {
        Category category = Category.builder()
                .name("category88")
                .build();
        Product product = com.example.grocerystore.model.Product.builder()
                .name("prod88")
                .unitFormat("88")
                .build();
        productService.save(product);
        return "redirect:/listallproductions";
    }

    @RequestMapping("/showaddAProductionForm")
    public String addProductionForm(Model model) {
        List<Category> categoryList = categoryService.findAll();
        Product product = new Product();
        model.addAttribute("production", product);
        model.addAttribute("categoryList", categoryList);
        return "addAProductionForm";
    }

    @RequestMapping("/showInventoryListForm")
    public String addInventoryForm(Model model) {
        List<Category> all = categoryService.findAll();
        model.addAttribute("categorynamelist", all);
        return "addInventoryListForm";
    }

    @RequestMapping("/showManyInventoryListForm")
    public String addManyInventoryForm(Model model) {
        List<Category> all = categoryService.findAll();
        model.addAttribute("categorynamelist", all);
        return "addManyInventoryListForm";
    }


    @RequestMapping("/fillmyproductdropdownlist")
    @ResponseBody
    public ArrayList<Product> fillMyProductDropdownList(@RequestParam("selectedValue") String selectedValue, Model model) {
        Category category = categoryService.findById(Long.valueOf(selectedValue));
        ArrayList<Product> productList = category.getProductList();
        return productList;
    }

}
