package com.example.EverExpanding.model.service;

import com.example.EverExpanding.model.entity.MediaEntity;

import java.time.LocalDateTime;

public class PostServiceModel {

    private String title;
    private String categories;
    private String description;
    private MediaEntity media;
    private LocalDateTime createdOn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public MediaEntity getMedia() {
        return media;
    }

    public void setMedia(MediaEntity media) {
        this.media = media;
    }
}
