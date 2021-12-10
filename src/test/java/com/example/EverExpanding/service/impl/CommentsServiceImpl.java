package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.Category;
import com.example.EverExpanding.model.entity.Comment;
import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.UserEntity;
import com.example.EverExpanding.model.service.CommentServiceModel;
import com.example.EverExpanding.model.view.CommentViewModel;
import com.example.EverExpanding.repository.CommentRepository;
import com.example.EverExpanding.repository.PostRepository;
import com.example.EverExpanding.service.CommentService;
import com.example.EverExpanding.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentsServiceImpl {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private final ModelMapper modelMapper = new ModelMapper();
    private Comment testComment;
    private CommentServiceImpl serviceToTest;
    private UserEntity testUser;
    private Post testPost;

    @BeforeEach
    void init() {
        serviceToTest = new CommentServiceImpl(commentRepository, userService, postService, modelMapper);
        testUser = new UserEntity();
        testPost = new Post();
        testUser.setUsername("test username");
        testUser.setEmail("email@email");
        testUser.setId(1L);
        testPost.setId(1L);


        testComment = new Comment();
        testComment.setLikes(0);
        testComment.setAuthor(testUser);
        testComment.setMessage("Message");
        testComment.setUser(testUser.getUsername());
        testComment.setPost(testPost);
        testComment.setId(1L);
        testComment.setCreated(LocalDateTime.now());

        testPost.setComments(List.of(testComment));
    }

    @Test
    void testFindByEmailAndMessage() {
        Mockito.when(userService.findByEmail("email@email"))
                .thenReturn(testUser);

        Mockito.when(commentRepository.findCommentByAuthorAndMessage(testUser, "Message"))
                .thenReturn(testComment);


        var actual = serviceToTest.findByEmailAndTextMessage(testUser.getEmail(), testComment.getMessage());

        Assertions.assertEquals(1, actual);
    }
    
}
