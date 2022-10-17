package com.example.forums_backend.repository;

import com.example.forums_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByEmail(String email);
    Optional<Account> findByEmail(String email);
    Optional<Account> findFirstByUsername(String username);
}
