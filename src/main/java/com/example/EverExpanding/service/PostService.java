package com.example.EverExpanding.service;

import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.service.PostServiceModel;
import com.example.EverExpanding.model.view.PostViewModelSummary;

import java.util.List;

public interface PostService {
    void savePost(PostServiceModel postServiceModel, String name, Long id);

    List<PostViewModelSummary> getAllPosts();

    PostViewModelSummary findById(Long id, String email);

    Post findPostById(Long routeId);

    void deleteOffer(Long id);

    boolean isOwner(String name, Long id);

    void updatePost(PostServiceModel serviceModel, Long id);
}
