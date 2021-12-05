package com.example.EverExpanding.model.binding;

import com.example.EverExpanding.model.entity.Post;
import com.example.EverExpanding.model.entity.ProfilePicture;
import com.example.EverExpanding.model.entity.Role;

import javax.validation.constraints.*;
import java.util.List;

public class UserRegisterBindingModel {

    private String username;
    private String email;
    private String password;
    private String repeatPass;

    public UserRegisterBindingModel() {
    }

    @NotEmpty
    @Size(min = 3, max = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    public String getRepeatPass() {
        return repeatPass;
    }

    public void setRepeatPass(String repeatPass) {
        this.repeatPass = repeatPass;
    }
}
