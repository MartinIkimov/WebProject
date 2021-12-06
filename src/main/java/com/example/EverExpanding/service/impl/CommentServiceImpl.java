package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Comment;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.service.CommentServiceModel;
import com.example.EverExpanding.model.view.CommentViewModel;
import com.example.EverExpanding.repository.CommentRepository;
import com.example.EverExpanding.service.CommentService;
import com.example.EverExpanding.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
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
    public CommentViewModel createComment(CommentServiceModel serviceModel) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        Objects.requireNonNull(serviceModel.getCreator());

        //TODO objectNotFound

        Post post = postService.findPostById(serviceModel.getPostId());
        UserEntity author = userService.findByEmail(serviceModel.getCreator());

        Comment comment = new Comment();
        comment.setMessage(serviceModel.getMessage());
        comment.setCreated(LocalDateTime.parse(formatDateTime, formatter));
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setUser(author.getUsername());

        Comment saved = commentRepository.save(comment);

        return mapAsComment(saved);
    }

    @Override
    public Long findByEmailAndTextMessage(String name, String message) {
        return commentRepository.findCommentByAuthorAndMessage(userService.findByEmail(name), message).getId();
    }

    private CommentViewModel mapAsComment(Comment comment) {
        CommentViewModel commentViewModel = new CommentViewModel();

        commentViewModel.setCommentId(comment.getId());
        commentViewModel.setCanApprove(true);
        commentViewModel.setCanDelete(true);
        commentViewModel.setCreated(comment.getCreated());
        commentViewModel.setMessage(comment.getMessage());
        commentViewModel.setUser(comment.getAuthor().getUsername());

        return commentViewModel;
    }
}
