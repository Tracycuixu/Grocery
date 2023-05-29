package com.example.grocerystore.controller;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.example.grocerystore.entity.AdminEntity;
import com.example.grocerystore.model.Admin;
import com.example.grocerystore.repository.AdminRepository;
import com.example.grocerystore.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInAdmin")
public class AdminController {

    private final AdminService adminService;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminController(AdminService adminService, AdminRepository adminRepository) {

        this.adminService = adminService;
        this.adminRepository = adminRepository;

    }
    @GetMapping("/list")
    public String listAdmins(Model model){
        List<Admin> admins =  adminService.getAllAdmins();
        model.addAttribute("admins", admins);
//        return "admins/list-admins";
        return "redirect:/listallproductions";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(@RequestParam("loggedInAdminname") String loggedInAdminname, Model model) {
        AdminEntity admin = new AdminEntity();
        model.addAttribute("Admin", admin);
        model.addAttribute("loggedInAdminname", loggedInAdminname);
        return "admins/admin-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("adminId") Long adminId, @RequestParam("loggedInAdminname") String loggedInAdminname, Model model) {
//        AdminEntity admin = adminService.findById(adminId);
        Admin admin = adminService.getAdminById(adminId);
        model.addAttribute("Admin", admin);
        model.addAttribute("loggedInAdminname", loggedInAdminname);
        return "admins/admin-form";
    }
    @PostMapping("/upsert")
    public String upsertAdmin(@Valid @ModelAttribute("admin") Admin theAdmin, BindingResult result){
        if (result.hasErrors()) {
            return "admins/admin-form";
        }
        adminService.save(theAdmin);
        return "redirect:/admin/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("adminId") Long theId){
        //delete the admin
        adminService.deleteAdmin(theId);
        return "redirect:/admin/list";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "admins/admin-login";
     }
    @PostMapping("/login")
    public String login(@RequestParam String adminname, @RequestParam String password, Model model, HttpSession session) {
        Optional<AdminEntity> admin = adminRepository.findByAdminname(adminname);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            session.setAttribute("loggedInAdmin", admin.get());
            return "redirect:/admin/list";
        } else {
            model.addAttribute("error", "Invalid adminname or password.");
//            return "/admins/admin-login";
            return "login";
        }
    }
    @ModelAttribute("loggedInAdmin")
    public AdminEntity getLoggedInAdmin(HttpSession session) {
        return (AdminEntity) session.getAttribute("loggedInAdmin");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInAdmin");
        return "login";
    }
}