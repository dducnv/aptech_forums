package com.example.forums_backend.repository;

import com.example.forums_backend.entity.TagFollowing;
import com.example.forums_backend.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagFollowingRepository extends JpaRepository<TagFollowing, Long> {
    Optional<TagFollowing> findFirstByTag_IdAndAccount_Id(Long tagId, Long accountId);
}
