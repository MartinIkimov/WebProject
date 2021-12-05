package com.example.EverExpanding.web;

import com.example.EverExpanding.model.binding.CommentBindingModel;
import com.example.EverExpanding.model.service.CommentServiceModel;
import com.example.EverExpanding.model.view.CommentViewModel;
import com.example.EverExpanding.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentRestController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/{postId}/comments")
    public ResponseEntity<List<CommentViewModel>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PostMapping("/api/{postId}/comments")
    public ResponseEntity<CommentViewModel> newComment(@PathVariable Long postId, @Valid CommentBindingModel commentBindingModel, Principal principal) {
        CommentServiceModel serviceModel = modelMapper.map(commentBindingModel, CommentServiceModel.class);
        serviceModel.setPostId(postId);
        serviceModel.setCreator(principal.getName());

        CommentViewModel newComment = commentService.createComment(serviceModel, principal);

//        URI locationOfNewComment = URI.create(String.format("/api/%s/comments/%s", postId, newComment.getCommentId()));
        URI locationOfNewComment = URI.create(String.format("/posts/%s/details", postId));
        return ResponseEntity.created(locationOfNewComment).body(newComment);
    }


}
