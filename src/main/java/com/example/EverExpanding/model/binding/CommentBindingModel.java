package com.example.EverExpanding.model.binding;

import javax.validation.constraints.NotBlank;

public class CommentBindingModel {

    private String message;

    @NotBlank
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
