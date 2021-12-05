package com.example.EverExpanding.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture extends BaseEntity {

    private UserEntity profile;
    private String url;
    private String publicId;

    public ProfilePicture() {
    }

    @OneToOne
    public UserEntity getProfile() {
        return profile;
    }

    public void setProfile(UserEntity profile) {
        this.profile = profile;
    }

    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column()
    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }
}
