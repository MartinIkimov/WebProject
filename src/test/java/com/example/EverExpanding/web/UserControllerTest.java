package com.example.EverExpanding.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;



import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.model.service.UserServiceModel;
import com.example.EverExpanding.repository.RoleRepository;
import com.example.EverExpanding.repository.UserRepository;
import com.example.EverExpanding.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

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

    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegisterUser() throws Exception {
        if (roleRepository.count() == 0) {
            Role userAdminRole = new Role();
            userAdminRole.setRole(RoleNameEnum.ADMIN);
            roleRepository.save(userAdminRole);
            Role userRole = new Role();
            userRole.setRole(RoleNameEnum.USER);
            roleRepository.save(userRole);
        }

//        UserServiceModel userServiceModel = new UserServiceModel();
//        String TEST_USERNAME = "UsernameToTest";
//        userServiceModel.setUsername(TEST_USERNAME);
//        String TEST_USER_EMAIL = "email@toTest.com";
//        userServiceModel.setEmail(TEST_USER_EMAIL);
//        userServiceModel.setPassword("12345");
//
//       userService.registerUserAndLogin(userServiceModel);
//
//       Assertions.assertEquals(1, userRepository.count());
//
//       UserEntity user = userService.findByEmail(TEST_USER_EMAIL);
//
//       Assertions.assertEquals(TEST_USERNAME, user.getUsername());

        mockMvc.
                perform(post("/users/register")
                        .param("username", "TEST_USERNAME")
                        .param("email", "TEST_USER_EMAIL@TEST.COM")
                        .param("password", "12345")
                        .param("repeatPass", "12345")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))

                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());

        Optional<UserEntity> newCreaterUserOp = userRepository.findByEmail("TEST_USER_EMAIL@TEST.COM");

        Assertions.assertTrue(newCreaterUserOp.isPresent());

        UserEntity userEntity = newCreaterUserOp.get();

        Assertions.assertEquals("TEST_USERNAME", userEntity.getUsername());
        Assertions.assertEquals("TEST_USER_EMAIL@TEST.COM", userEntity.getEmail());
    }


    @Test
    public void testPasswordsNotMatch() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("username", "Peter")
                        .param("email", "pesho@pesho2")
                        .param("password", "12345")
                        .param("repeatPass", "123456")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(0, userRepository.count());
    }

    @Test
    void testOpenLoginForm() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testEmailNotUnique() throws Exception {
        Role role = new Role();
        role.setRole(RoleNameEnum.USER);
        roleRepository.save(role);
        UserEntity user = new UserEntity();
        user.setLikes(0);
        user.setPassword("12345");
        user.setEmail("email@email");
        user.setUsername("Email");
        user.setRoles(List.of(role));
        user.setProfilePicture(null);
        userRepository.save(user);

        mockMvc.perform(post("/users/register")
                        .param("username", "OFF")
                        .param("email", "email@email")
                        .param("password", "12345")
                        .param("repeatPass", "12345")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());
    }

    @Test
    void testUsernameNotUnique() throws Exception {
        Role role = new Role();
        role.setRole(RoleNameEnum.USER);
        roleRepository.save(role);
        UserEntity user = new UserEntity();
        user.setLikes(0);
        user.setPassword("12345");
        user.setEmail("email@email");
        user.setUsername("Email");
        user.setRoles(List.of(role));
        user.setProfilePicture(null);
        userRepository.save(user);

        mockMvc.perform(post("/users/register")
                        .param("username", "Email")
                        .param("email", "OFF@OFF")
                        .param("password", "12345")
                        .param("repeatPass", "12345")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection());

        Assertions.assertEquals(1, userRepository.count());
    }
}
