package com.example.grocerystore.service;

import com.example.grocerystore.model.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();

    void save(Admin admin);

    Admin getAdminById(Long adminId);

    void deleteAdmin(Long adminId);

    Admin findByAdminname(String adminname);
}

