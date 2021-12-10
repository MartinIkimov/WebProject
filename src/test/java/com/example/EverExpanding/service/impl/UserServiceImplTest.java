package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.model.service.UserServiceModel;
import com.example.EverExpanding.model.view.UserViewModel;
import com.example.EverExpanding.repository.RoleRepository;
import com.example.EverExpanding.repository.UserRepository;
import com.example.EverExpanding.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private UserEntity testUser;
    private Role role;


    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRole(RoleNameEnum.ADMIN);
        roleRepository.save(role);

        testUser = new UserEntity();
        testUser.setPassword("password");
        testUser.setUsername("user");
        testUser.setEmail("user@user");
        testUser.setLikes(0);
        testUser.setProfilePicture(null);
        testUser.setRoles(List.of(role));

        testUser = userRepository.save(testUser);
    }

    @Test
    void testFindByEmail() {
        UserEntity user = new UserEntity();;
        user.setPassword("password");
        user.setUsername("user2");
        user.setEmail("user2@user2");
        user.setLikes(0);
        user.setProfilePicture(null);
        user.setRoles(List.of(role));

        userRepository.save(user);

        UserEntity findByEmailUser = userService.findByEmail(user.getEmail());
        String emailOfFoundUser = findByEmailUser.getEmail();
        Assertions.assertEquals(emailOfFoundUser, user.getEmail());
    }

    @Test
    void testFindById() {
        UserViewModel findByEmailUser = userService.findById(testUser.getId());
        String emailOfFoundUser = findByEmailUser.getUsername();
        Assertions.assertEquals(emailOfFoundUser, testUser.getUsername());
    }

    @Test
    void testUsernameExists() {
        Assertions.assertTrue(userService.usernameExists("user"));
    }

    @Test
    void testEmailExists() {
        Assertions.assertTrue(userService.emailExists("user@user"));
    }

    @Test
    void registerUserTest() {

        UserServiceModel userServiceModel = new UserServiceModel();

        userServiceModel.setPassword("password");
        userServiceModel.setUsername("pesho");
        userServiceModel.setEmail("pesho@peshev.com");

        userService.registerUserAndLogin(userServiceModel);

        Assertions.assertEquals(2, userRepository.count());
    }
}
