package com.example.EverExpanding.web;

import com.example.EverExpanding.model.binding.CommentBindingModel;
import com.example.EverExpanding.model.entity.*;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser("lucho@example.com")
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

    private static final String COMMENT_1 = "hey Spring is cool!";
    private static final String COMMENT_2 = "Well... it is a bit trick sometimes... :(";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setRole(RoleNameEnum.USER);
        role = roleRepository.save(role);

        testUser = new UserEntity();
        testUser.setPassword("password");
        testUser.setUsername("lucho");
        testUser.setEmail("lucho@example.com");
        testUser.setProfilePicture(null);
        testUser.setRoles(List.of(role));
        testUser.setLikes(0);

        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetComments() throws Exception {
        var route = initComments(initRoute());

        mockMvc.perform(get("/api/" + route.getId() + "/comments")).
                andExpect(status().isOk()).
                andExpect(jsonPath("$", hasSize(2))).
                andExpect(jsonPath("$.[0].message", is(COMMENT_1))).
                andExpect(jsonPath("$.[1].message", is(COMMENT_2)));
    }

    @Test
    void testCreateComments() throws Exception {
        CommentBindingModel testComment = new CommentBindingModel();
        testComment.setMessage(COMMENT_1);

        var emptyRoute = initRoute();

        mockMvc.perform(
                        post("/api/" + emptyRoute.getId() + "/comments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testComment))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", MatchesPattern.matchesPattern("/api/" + emptyRoute.getId() + "/comments/\\d")))
                .andExpect(jsonPath("$.message").value(is(COMMENT_1)));

    }

    private Post initRoute() {
        Category category = new Category();
        category.setName("fun");
        category = categoryRepository.save(category);

        Post testRoute = new Post();
        testRoute.setTitle("title");
        testRoute.setDescription("desc");
        testRoute.setCategories(List.of(category));
        testRoute.setMedia(null);
        testRoute.setCreatedOn(LocalDateTime.now());
        testRoute.setLikes(0);
        testRoute.setAuthor(testUser);
        return postRepository.save(testRoute);
    }

    private Post initComments(Post route) {

        Comment comment1 = new Comment();
        comment1.setCreated(LocalDateTime.now());
        comment1.setAuthor(testUser);
        comment1.setPost(route);
        comment1.setUser(route.getAuthor().getUsername());
        comment1.setMessage(COMMENT_1);
        comment1.setLikes(0);

        Comment comment2 = new Comment();
        comment2.setCreated(LocalDateTime.now());
        comment2.setAuthor(testUser);
        comment2.setPost(route);
        comment2.setUser(route.getAuthor().getUsername());
        comment2.setMessage(COMMENT_2);
        comment2.setLikes(0);

        comment1 = commentRepository.save(comment1);
        comment2 = commentRepository.save(comment2);

        route.setComments(List.of(comment1, comment2));

        return postRepository.save(route);
    }
}

