package com.example.EverExpanding.web;

import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WithMockUser(value = "lucho@example.com", roles = "ADMIN")
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setRole(RoleNameEnum.ADMIN);
        roleRepository.save(role);

        testUser = new UserEntity();
        testUser.setPassword("password");
        testUser.setUsername("lucho");
        testUser.setEmail("lucho@example.com");
        testUser.setProfilePicture(null);
        testUser.setRoles(List.of(role));
        testUser.setLikes(0);

        testUser = userRepository.save(testUser);
    }

    @Test
    void testOpenStatistics() throws Exception {
        mockMvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(view().name("stats"));
    }
}
