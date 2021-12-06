package com.example.EverExpanding.web;

import com.example.EverExpanding.model.binding.PostBindingModel;
import com.example.EverExpanding.model.entity.MediaEntity;
import com.example.EverExpanding.model.service.PostServiceModel;
import com.example.EverExpanding.service.CloudinaryImage;
import com.example.EverExpanding.service.CloudinaryService;
import com.example.EverExpanding.service.MediaService;
import com.example.EverExpanding.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private final CloudinaryService cloudinaryService;
    private final MediaService mediaService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    public PostsController(CloudinaryService cloudinaryService, MediaService mediaService, PostService postService, ModelMapper modelMapper) {
        this.cloudinaryService = cloudinaryService;
        this.mediaService = mediaService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String post () {
        return "add-post";
    }

    @PostMapping("/add")
    public String addPost(@Valid PostBindingModel postBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) throws IOException {
        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("postModel", postBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.postModel", bindingResult);
            return "redirect:add";
        }

        PostServiceModel postServiceModel = modelMapper.map(postBindingModel, PostServiceModel.class);
        MediaEntity media = null;

        if(postBindingModel.getMultipartFile().getSize() > 0) {
             media = createMediaEntity(postBindingModel.getMultipartFile());
            mediaService.saveMedia(media);
        }
        if(media != null) {
            postService.savePost(modelMapper.map(postServiceModel, PostServiceModel.class), principal.getName(), media.getId());
        } else {
            postService.savePost(modelMapper.map(postServiceModel, PostServiceModel.class), principal.getName(), null);
        }


        return "redirect:all";
    }

    @GetMapping("/all")
    public String allPosts(Model model) {
        model.addAttribute("posts",
                postService.getAllPosts());
        return "posts";
    }

    @GetMapping("/{id}/details")
    public String postDetails(Model model, @PathVariable Long id) {
        model.addAttribute("post", this.postService.findById(id));
        return "post-details";
    }

    private MediaEntity createMediaEntity(MultipartFile file) throws IOException {
        final CloudinaryImage uploaded = this.cloudinaryService.upload(file);
        MediaEntity media = new MediaEntity();
        media.setUrl(uploaded.getUrl());
        media.setTitle(file.getOriginalFilename());
        media.setPublicId(uploaded.getPublicId());

        return media;
    }


    @ModelAttribute("postModel")
    public PostBindingModel postBindingModel() {
        return new PostBindingModel();
    }
}
