package com.example.forums_backend.service;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountManagerService {
    @Autowired
    AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
