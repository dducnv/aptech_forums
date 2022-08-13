package com.example.forums_backend.service;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.dto.RegisterDto;
import com.example.forums_backend.entity.dto.UserInfoDto;
import com.example.forums_backend.exception.AccountException;
import com.example.forums_backend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public RegisterDto register(RegisterDto accountRegisterDto) throws AccountException {
        Optional<Account> account = Optional.ofNullable(accountRepository.findAccountByEmail(accountRegisterDto.getEmail()));
        if (account.isPresent()) {
           throw new AccountException("Account is exist");
        }
        if(!accountRegisterDto.getPassword().equals(accountRegisterDto.getConfirmPassword())) {
            throw new AccountException("Password is not match");
        }
        Account newAccount = Account.builder()
                .name(accountRegisterDto.getName())
                .email(accountRegisterDto.getEmail())
                .phone(accountRegisterDto.getPhone())
                .password(passwordEncoder.encode(accountRegisterDto.getPassword()))
                .role("USER")
                .build();
        accountRepository.save(newAccount);
        accountRegisterDto.setEmail(newAccount.getEmail());
        return accountRegisterDto;

    }
    public UserInfoDto getUserInfo() {
        Object userInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findAccountByEmail(userInfo.toString()));
        if(!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        Account account = optionalAccount.get();
        return UserInfoDto.builder()
                .name(account.getName())
                .email(account.getEmail())
                .role(account.getRole())
                .build();
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByEmail(email);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));
        return new User(account.getEmail(), account.getPassword(), authorities);
    }

}
