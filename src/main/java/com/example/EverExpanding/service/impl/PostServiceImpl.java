package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Category;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.Role;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.entity.enums.RoleNameEnum;
import com.example.EverExpanding.model.service.PostServiceModel;
import com.example.EverExpanding.model.view.PostViewModelSummary;
import com.example.EverExpanding.repository.PostRepository;
import com.example.EverExpanding.service.CategoryService;
import com.example.EverExpanding.service.MediaService;
import com.example.EverExpanding.service.PostService;
import com.example.EverExpanding.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);


        Post post = modelMapper.map(postServiceModel, Post.class);
        post.setAuthor(userService.findByEmail(email));
        separateCategoriesIntoList(postServiceModel, post);
        post.setCreatedOn(LocalDateTime.parse(formatDateTime, formatter));

        if(id != null) {
            post.setMedia(mediaService.findMediaById(id));
        }
        postRepository.save(post);
    }

    @Transactional
    @Override
    @Cacheable("cachedPosts")
    public List<PostViewModelSummary> getAllPosts() {

        return postRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, PostViewModelSummary.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostViewModelSummary findById(Long id, String username) {
        Post post = postRepository.findById(id).orElse(null);
        PostViewModelSummary postViewModelSummary = modelMapper.map(post, PostViewModelSummary.class);
        postViewModelSummary.setCanDelete(isOwner(username, id));

        return postViewModelSummary;
    }

    @Override
    public Post findPostById(Long routeId) {
        return postRepository.findById(routeId).orElse(null);
    }

    @Override
    public void deleteOffer(Long id) {
        postRepository.deleteById(id);
    }

    public boolean isOwner(String username, Long id) {
        Optional<Post> postOpt = postRepository.findById(id);
        UserEntity caller = userService.findByEmail(username);

        if(postOpt.isEmpty() || caller == null) {
            return false;
        } else {
            Post post = postOpt.get();

            return isAdmin(caller) || post.getAuthor().getEmail().equals(username);
        }
    }

    @Override
    public void updatePost(PostServiceModel serviceModel, Long id) {
        Post post = postRepository.findById(serviceModel.getId()).orElse(null);
        separateCategoriesIntoList(serviceModel, post);

        post.setTitle(serviceModel.getTitle());
        post.setMedia(serviceModel.getMedia());
        post.setDescription(serviceModel.getDescription());

        if(id != null) {
            post.setMedia(mediaService.findMediaById(id));
        }

        postRepository.save(post);
    }

    private void separateCategoriesIntoList(PostServiceModel serviceModel, Post post) {
        String[] categories = serviceModel.getCategories().split(", ");
        List<Category> categoryList = new ArrayList<>();
        for (String category : categories) {
            Category cat = new Category();
            cat.setName(category);
            categoryList.add(cat);
            categoryService.saveCategory(cat);
        }
        post.setCategories(categoryList);
    }

    private boolean isAdmin(UserEntity user) {
        return user.getRoles()
                .stream()
                .map(Role::getRole)
                .anyMatch(r -> r == RoleNameEnum.ADMIN);
    }
}
