package com.example.forums_backend.repository;

import com.example.forums_backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
    Optional<Post> findFirstBySlug(String slug);

    Optional<Post> findByIdAndAuthor_Id(Long id, Long author_id);

    List<Post> findByAuthor_id(Long id);
}
