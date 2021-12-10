package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Category;
import com.example.EverExpanding.model.entity.MediaEntity;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.service.PostServiceModel;
import com.example.EverExpanding.repository.MediaRepository;
import com.example.EverExpanding.repository.PostRepository;
import com.example.EverExpanding.repository.UserRepository;
import com.example.EverExpanding.service.CategoryService;
import com.example.EverExpanding.service.CloudinaryService;
import com.example.EverExpanding.service.MediaService;
import com.example.EverExpanding.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {


    @Mock
    private PostRepository mockPostRepository;

    @Mock
    private UserService mockUserService;

    @Mock
    private CategoryService mockCategoryService;

    @Mock
    private MediaService mockMediaService;

    private final ModelMapper modelMapper = new ModelMapper();

    Post testPost;
    private UserEntity testUser;

    private PostServiceImpl postServiceToTest;


    @BeforeEach
    void init() {
        postServiceToTest = new PostServiceImpl(mockPostRepository, mockUserService, modelMapper, mockCategoryService, mockMediaService);
        Category testCategory = new Category();
        testCategory.setName("fun");

        testPost = new Post();
        testPost.setLikes(0);
        testPost.setAuthor(testUser);
        testPost.setCreatedOn(LocalDateTime.now());
        testPost.setMedia(new MediaEntity());
        testPost.setCategories(List.of(testCategory));
        testPost.setDescription("description");
        testPost.setTitle("title");
        testPost.setId(1L);
    }

    @Test
    void testFindById() {
        Mockito.when(mockPostRepository.findById(testPost.getId()))
                .thenReturn(Optional.of(testPost));

        var actual = postServiceToTest.findById(testPost.getId());

        Assertions.assertEquals(testPost.getTitle(), actual.getTitle());
    }

    @Test
    void testFindPostById() {
        Mockito.when(mockPostRepository.findById(testPost.getId()))
                .thenReturn(Optional.of(testPost));

        var actual = postServiceToTest.findPostById(testPost.getId());

        Assertions.assertEquals(testPost.getTitle(), actual.getTitle());
    }

    @Test
    void testGetAllPosts() {
        Mockito.when(mockPostRepository.findAll())
                .thenReturn(List.of(testPost));

        var actual = postServiceToTest.getAllPosts();

        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void testAddPost() {
        PostServiceModel postServiceModelTest = modelMapper.map(testPost, PostServiceModel.class);

        Mockito.when(mockPostRepository.save(Mockito.any(Post.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        postServiceToTest.savePost(postServiceModelTest, "pesho@pesho", null);

        Assertions.assertEquals("title", postServiceModelTest.getTitle());
    }
}
