package com.example.EverExpanding.service.impl;

import com.example.EverExpanding.model.entity.MediaEntity;
import com.example.EverExpanding.repository.MediaRepository;
import com.example.EverExpanding.service.MediaService;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;

    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Override
    public void saveMedia(MediaEntity media) {
        mediaRepository.save(media);
    }

    @Override
    public MediaEntity findMediaById(Long id) {
        return mediaRepository.findById(id).orElse(null);
    }
}
