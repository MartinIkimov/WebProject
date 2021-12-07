package com.example.EverExpanding.service;

import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.service.UserServiceModel;
import com.example.EverExpanding.model.view.UserViewModel;

public interface UserService {

    void registerUserAndLogin(UserServiceModel newUser);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    UserEntity findByEmail(String email);

    UserViewModel findById(Long id);
}
