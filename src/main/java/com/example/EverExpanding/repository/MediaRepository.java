package com.example.EverExpanding.repository;

import com.example.EverExpanding.model.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<MediaEntity, Long> {
}
