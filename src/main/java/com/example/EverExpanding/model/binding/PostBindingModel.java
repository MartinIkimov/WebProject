package com.example.EverExpanding.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class PostBindingModel {
    private String title;
    private String categories;
    private String description;
    private MultipartFile multipartFile;

    public PostBindingModel() {
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
