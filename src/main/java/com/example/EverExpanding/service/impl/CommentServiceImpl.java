package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Comment;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.service.CommentServiceModel;
import com.example.EverExpanding.model.view.CommentViewModel;
import com.example.EverExpanding.repository.CommentRepository;
import com.example.EverExpanding.service.CommentService;
import com.example.EverExpanding.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserServiceImpl userService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userService, PostService postService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public List<CommentViewModel> getComments(Long postId) {
        Post post = postService.findPostById(postId);

        return post.getComments()
                .stream()
                .map(c -> modelMapper.map(c, CommentViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CommentViewModel createComment(CommentServiceModel serviceModel, Principal principal) {
        Comment newComment = modelMapper.map(serviceModel, Comment.class);
        newComment.setAuthor(userService.findByEmail(principal.getName()));
        newComment.setUser(newComment.getAuthor().getUsername());
        newComment.setCreated(LocalDateTime.now());
        CommentViewModel comment = modelMapper.map(serviceModel, CommentViewModel.class);
        comment.setUser(userService.findByEmail(principal.getName()).getUsername());
        commentRepository.save(newComment);
        return comment;
    }

    @Override
    public Long findByEmailAndTextMessage(String name, String message) {
        return commentRepository.findCommentByAuthorAndMessage(userService.findByEmail(name), message).getId();
    }
}
