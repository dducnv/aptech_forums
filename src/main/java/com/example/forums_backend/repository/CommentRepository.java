package com.example.forums_backend.repository;

import com.example.forums_backend.entity.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long id,Sort sort);

    List<Comment> findCommentByAccount_Id(Long id);
}
