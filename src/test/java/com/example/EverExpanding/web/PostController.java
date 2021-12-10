package com.example.EverExpanding.web;

import com.example.EverExpanding.model.entity.Category;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.repository.CategoryRepository;
import com.example.EverExpanding.repository.PostRepository;
import com.example.EverExpanding.repository.RoleRepository;
import com.example.EverExpanding.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("peter@peter.com")
public class PostController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        Role userRole = new Role();
        userRole.setRole(RoleNameEnum.USER);
        roleRepository.save(userRole);


        testUser = new UserEntity();

        testUser.setPassword("12345");
        testUser.setUsername("Peter");
        testUser.setEmail("peter@peter.com");
        testUser.setProfilePicture(null);
        testUser.setLikes(0);
        testUser.setRoles(List.of(userRole));

        testUser = userRepository.save(testUser);
    }

    @Test
    void testOpenAddPost() throws Exception {
        mockMvc.perform(get("/posts/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-post"));
    }

    @Test
    void testOpenAllPosts() throws Exception {
        mockMvc.perform(get("/posts/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts"));
    }

    @Test
    void testOpenPostDetails() throws Exception {
        Category category = new Category();
        category.setName("fun");
        category = categoryRepository.save(category);

        Post post = new Post();
        post.setCreatedOn(LocalDateTime.now());
        post.setCategories(List.of(category));
        post.setMedia(null);
        post.setDescription("description");
        post.setLikes(0);
        post.setAuthor(testUser);
        post.setTitle("title");
        post = postRepository.save(post);

        testUser.setPosts(List.of(post));

        mockMvc.perform(get("/posts/" + post.getId() + "/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("post-details"));
    }
}
