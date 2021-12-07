package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.model.service.UserServiceModel;
import com.example.EverExpanding.model.view.UserViewModel;
import com.example.EverExpanding.repository.UserRepository;
import com.example.EverExpanding.service.RoleService;
import com.example.EverExpanding.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final EverExpandingUserServiceImpl everExpandingUserService;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleService roleService, UserDetailsService userDetailsService, ModelMapper modelMapper, EverExpandingUserServiceImpl everExpandingUserService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.everExpandingUserService = everExpandingUserService;
    }



    @Override
    public void registerUserAndLogin(UserServiceModel newUser) {
//        String password = passwordEncoder.encode(newUser.getPassword());
        UserEntity user = modelMapper.map(newUser, UserEntity.class);

        if(userRepository.count() == 0) {
            user.setRoles(List.of(roleService.findByRole(RoleNameEnum.ADMIN), roleService.findByRole(RoleNameEnum.USER)));
        } else {
            user.setRoles(List.of(roleService.findByRole(RoleNameEnum.USER)));
        }
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setLikes(0);

        userRepository.save(user);
// this is the spring repsresentation of a user

        UserDetails principal = everExpandingUserService.loadUserByUsername(user.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, user.getPassword(), principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    @Override
    public UserEntity findByEmail(String email) {
        // TODO or else throw with custom error
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    @Override
    public UserViewModel findById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        return modelMapper.map(user, UserViewModel.class);
    }

}
