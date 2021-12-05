package com.example.EverExpanding.service;

import com.example.EverExpanding.model.entity.MediaEntity;

public interface MediaService {
    void saveMedia(MediaEntity media);

    MediaEntity findMediaById(Long id);
}
