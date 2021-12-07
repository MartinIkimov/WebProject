package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EverExpandingUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public EverExpandingUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // The purpose of this method is to map our user representation (UserEntity)
        // to the user representation in the spring sercurity world (UserDetails).
        // The only thing that spring will provide to us is the user name.
        // The user name will come from the HTML login form.

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));

        return mapToUserDetails(userEntity);
    }

    private UserDetails mapToUserDetails(UserEntity userEntity) {
        // GrantedAuthority is the representation of a user role in the
        // spring world. SimpleGrantedAuthority is an implementation of GrantedAuthority
        // which spring provides for our convenience.
        // Our representation of role is Role

        List<GrantedAuthority> authorityList =
                userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                        .collect(Collectors.toList());
        // User is the spring implementation of UserDetails interface.
        return new EverExpandingUser(userEntity.getEmail(), userEntity.getPassword(), authorityList);
    }
}
