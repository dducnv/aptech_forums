package com.example.forums_backend.service;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountManagerService {
    AccountRepository accountRepository;

    @Autowired
    public AccountManagerService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account update(Account account, Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        Account accountModal = optionalAccount.get();
        accountModal.setRole(account.getRole());
        return accountRepository.save(accountModal);
    }

    public void delete(Long id){
       accountRepository.deleteById(id);
    }
}
