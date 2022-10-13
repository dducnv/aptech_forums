package com.example.forums_backend.repository;

import com.example.forums_backend.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Voting, Long> {
    Optional<Voting> findFirstByPost_IdAndAccount_Id(Long postId, Long accountId);
}
