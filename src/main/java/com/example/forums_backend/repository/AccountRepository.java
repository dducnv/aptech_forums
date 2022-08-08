package com.example.forums_backend.repository;

import com.example.forums_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByEmail(String email);
}
