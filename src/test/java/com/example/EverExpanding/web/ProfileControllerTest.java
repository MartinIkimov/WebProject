package com.example.EverExpanding.web;

import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.repository.RoleRepository;
import com.example.EverExpanding.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
    private UserEntity testUser;

    @PostConstruct
    void setUp() {
        Role userRole = new Role();
        userRole.setRole(RoleNameEnum.USER);
        roleRepository.save(userRole);


        testUser = new UserEntity();

        testUser.setPassword("12345");
        testUser.setUsername("Peter");
        testUser.setEmail("peter@peter");
        testUser.setProfilePicture(null);
        testUser.setLikes(0);
        testUser.setRoles(List.of(userRole));

        testUser = userRepository.save(testUser);
    }

    @Test
    @WithUserDetails(value = "peter@peter")
    void testOpenProfile() throws Exception {
        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    @Test
    @WithUserDetails(value = "peter@peter")
    void testOpenProfileWithId() throws Exception {
        mockMvc.perform(get("/users/profile/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }
}
