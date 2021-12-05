package com.example.EverExpanding.repository;

import com.example.EverExpanding.model.entity.Comment;
import com.example.EverExpanding.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByAuthorAndMessage(UserEntity author, String message);
}
