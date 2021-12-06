package com.example.EverExpanding.model.entity;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity{
    private UserEntity author;
    private LocalDateTime createdOn;
    private String title;
    private String description;
    private Integer likes = 0;
    private List<Comment> comments;
    //for picture or video in claudinary
    private MediaEntity media;
    private List<Category> categories;

    public Post() {
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Column
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String topic) {
        this.title = topic;
    }

    @Column(nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }


    @OneToMany(mappedBy = "post")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    @ManyToMany(fetch = FetchType.LAZY)
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    @OneToOne
    public MediaEntity getMedia() {
        return media;
    }

    public void setMedia(MediaEntity media) {
        this.media = media;
    }
}
