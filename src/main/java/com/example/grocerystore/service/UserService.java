package com.example.grocerystore.service;

import com.example.grocerystore.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void save(User user);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    User findByUsername(String username);
}

