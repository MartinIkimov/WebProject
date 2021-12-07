package com.example.EverExpanding.model.view;

import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.ProfilePicture;
import com.example.EverExpanding.model.entity.Role;

import java.util.List;

public class UserViewModel {

    private Long id;
    private String username;
    private Integer likes;
    private ProfilePicture profilePicture;
    private List<Role> roles;
    private List<Post> posts;

    public UserViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
