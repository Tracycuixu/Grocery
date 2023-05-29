package com.example.grocerystore.service;

import com.example.grocerystore.entity.UserEntity;
import com.example.grocerystore.mapper.MapperHelper;
import com.example.grocerystore.model.User;
import com.example.grocerystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperHelper mapperHelper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, MapperHelper mapperHelper) {
        this.userRepository = userRepository;
        this.mapperHelper = mapperHelper;
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return mapperHelper.convertUserEntityListToUserList(userEntities);
    }

    @Override
    public void save(User user) {
        UserEntity entity = mapperHelper.convertUserToUserEntity(user);
        userRepository.save(entity);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<UserEntity> foundUser = userRepository.findById(userId);
        return (User) foundUser.map(mapperHelper::convertUserEntityToUser).orElse(null);
    }

    @Override
    public void deleteUser(Long UserId) {
        userRepository.deleteById(UserId);
    }
    @Override
    public User findByUsername(String user_name) {
        Optional<UserEntity> foundUser = userRepository.findByUsername(user_name);
        return (User) foundUser.map(mapperHelper::convertUserEntityToUser).orElse(null);
    }
}
