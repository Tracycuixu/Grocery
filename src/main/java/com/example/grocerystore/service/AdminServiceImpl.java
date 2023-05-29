package com.example.grocerystore.service;

import com.example.grocerystore.entity.AdminEntity;
import com.example.grocerystore.mapper.MapperHelper;
import com.example.grocerystore.model.Admin;
import com.example.grocerystore.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final MapperHelper mapperHelper;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, MapperHelper mapperHelper) {
        this.adminRepository = adminRepository;
        this.mapperHelper = mapperHelper;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<AdminEntity> adminEntities = adminRepository.findAll();
        return mapperHelper.convertAdminEntityListToAdminList(adminEntities);
    }

//    @Override
//    public void save(Admin admin) {
//        AdminEntity entity = mapperHelper.convertAdminToAdminEntity((org.apache.catalina.Admin) admin);
//        adminRepository.save(entity);
//    }

    @Override
    public void save(Admin admin) {
        AdminEntity entity = mapperHelper.convertAdminToAdminEntity(admin);
        adminRepository.save(entity);
    }

    @Override
    public Admin getAdminById(Long adminId) {
        Optional<AdminEntity> foundAdmin = adminRepository.findById(adminId);
        return (Admin) foundAdmin.map(mapperHelper::convertAdminEntityToAdmin).orElse(null);
    }

    @Override
    public void deleteAdmin(Long AdminId) {
        adminRepository.deleteById(AdminId);
    }
    @Override
    public Admin findByAdminname(String admin_name) {
        Optional<AdminEntity> foundAdmin = adminRepository.findByAdminname(admin_name);
        return (Admin) foundAdmin.map(mapperHelper::convertAdminEntityToAdmin).orElse(null);
    }

}
