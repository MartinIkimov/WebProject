package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Category;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.service.PostServiceModel;
import com.example.EverExpanding.model.view.PostViewModelSummary;
import com.example.EverExpanding.repository.PostRepository;
import com.example.EverExpanding.service.CategoryService;
import com.example.EverExpanding.service.MediaService;
import com.example.EverExpanding.service.PostService;
import com.example.EverExpanding.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final MediaService mediaService;

    public PostServiceImpl(PostRepository postRepository, UserService userService, ModelMapper modelMapper, CategoryService categoryService, MediaService mediaService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.mediaService = mediaService;
    }

    @Override
    public void savePost(PostServiceModel postServiceModel, String email, Long id) {
        Post post = modelMapper.map(postServiceModel, Post.class);
        post.setAuthor(userService.findByEmail(email));
        String[] categories = postServiceModel.getCategories().split(", ");
        List<Category> categoryList = new ArrayList<>();
        for (String category : categories) {
            Category cat = new Category();
            cat.setName(category);
            categoryList.add(cat);
            categoryService.saveCategory(cat);
        }
        post.setCategories(categoryList);
        post.setCreatedOn(LocalDateTime.now());
        if(id != null) {
            post.setMedia(mediaService.findMediaById(id));
        }
        postRepository.save(post);
    }

    @Transactional
    @Override
    public List<PostViewModelSummary> getAllPosts() {
        List<Post> test = postRepository.findAll();
        List<PostViewModelSummary> posts = postRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PostViewModelSummary.class))
                .collect(Collectors.toList());

        return posts;
    }

    @Transactional
    @Override
    public PostViewModelSummary findById(Long id) {
        return modelMapper.map(postRepository.findById(id).orElse(null), PostViewModelSummary.class);
    }

    @Override
    public Post findPostById(Long routeId) {
        return postRepository.findById(routeId).orElse(null);
    }
}
