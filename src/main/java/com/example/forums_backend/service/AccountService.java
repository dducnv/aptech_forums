package com.example.forums_backend.service;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.EmailDetails;
import com.example.forums_backend.entity.dto.*;
import com.example.forums_backend.entity.my_enum.AuthProvider;
import com.example.forums_backend.exception.AccountException;
import com.example.forums_backend.repository.AccountRepository;
import com.example.forums_backend.utils.GeneratingPassword;
import com.example.forums_backend.utils.JwtUtil;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {
    private int expireTime = 60 * 1000 * 5;

    public static final Pattern STUDENT_FPT_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@fpt.edu.vn$", Pattern.CASE_INSENSITIVE);

    public static final Pattern STAFF_FPT_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Za-z0-9._%+-]+@fe.edu.vn$", Pattern.CASE_INSENSITIVE);

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;
    @Autowired
    private TemplateEngine templateEngine;



    public CredentialDto loginWithOTP(LoginDto loginDto) throws AccountException {
        Optional<Account> account = Optional.ofNullable(accountRepository.findAccountByEmail(loginDto.getEmail()));
        if (!account.isPresent()) {
            throw new AccountException("Account not exist");
        }
        Account userInfo = account.get();
        boolean result = passwordEncoder.matches(loginDto.getPassword(), userInfo.getOne_time_password());
        if (!result) throw new AccountException("Password not match");
        boolean expireTime = true;
        if (new Date(System.currentTimeMillis()).before(userInfo.getExpire_time())) expireTime = false;
        if (expireTime) throw new AccountException("OTP IS EXPIRE");
        String accessToken = JwtUtil.generateToken(userInfo.getEmail(), userInfo.getRole(), "APTECH", JwtUtil.ONE_DAY * 7);
        // generate refresh token
        String refreshToken = JwtUtil.generateToken(userInfo.getEmail(), userInfo.getRole(), "APTECH", JwtUtil.ONE_DAY * 14);
        return CredentialDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(System.currentTimeMillis() + JwtUtil.ONE_DAY * 7)
                .tokenType("Bearer")
                .scope("basic_info")
                .build();
    }

    public CheckAccount getOtp(LoginEmailDto loginEmailDto) {
        try {
            Optional<Account> account = Optional.ofNullable(accountRepository.findAccountByEmail(loginEmailDto.getEmail()));
            if (!account.isPresent()) {
                return CheckAccount.builder()
                        .accountExist(false)
                        .build();
            }
            String password = String.valueOf(GeneratingPassword.generatePassword(6));
            Account accountUpdate = account.get();
            accountUpdate.setOne_time_password(passwordEncoder.encode(password));
            accountUpdate.setExpire_time(new Date(System.currentTimeMillis() + expireTime));
            if(!accountUpdate.isEmail_verify()){
                accountUpdate.setEmail_verify(true);
            }
            accountRepository.save(accountUpdate);
            Context context = new Context();
            context.setVariable("password", password);
            String template = templateEngine.process("one_time_password", context);
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setRecipient(accountUpdate.getEmail());
            emailDetails.setMsgBody(template);
            emailDetails.setSubject("OTP LOGIN");
            emailService.sendSimpleMail(emailDetails);

            return CheckAccount.builder()
                    .accountExist(true)
                    .build();
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
            throw new RuntimeException(exception);
        }

    }

    public RegisterDto register(RegisterDto accountRegisterDto) throws AccountException {
        Optional<Account> account = Optional.ofNullable(accountRepository.findAccountByEmail(accountRegisterDto.getEmail()));
        if (account.isPresent()) {
            throw new AccountException("Account is exist");
        }
        Matcher studentMatcher = STUDENT_FPT_EMAIL_ADDRESS_REGEX.matcher(accountRegisterDto.getEmail());
        Matcher staffMatcher = STAFF_FPT_EMAIL_ADDRESS_REGEX.matcher(accountRegisterDto.getEmail());
        boolean isFptMember = false;
        if(studentMatcher.find() || staffMatcher.find()){
            isFptMember = true;
        }
        Account newAccount = Account.builder()
                .imageUrl(accountRegisterDto.getAvatar())
                .name(accountRegisterDto.getName())
                .email(accountRegisterDto.getEmail())
                .email_verify(false)
                .fpt_member(isFptMember)
                .provider(AuthProvider.local)
                .role("USER")
                .build();
        accountRepository.save(newAccount);
        accountRegisterDto.setEmail(newAccount.getEmail());
        return accountRegisterDto;

    }

    public UserInfoDto getUserInfo() {
        Object userInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> optionalAccount = Optional.ofNullable(accountRepository.findAccountByEmail(userInfo.toString()));
        if (!optionalAccount.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        Account account = optionalAccount.get();
        return UserInfoDto.builder()
                .avatar(account.getImageUrl())
                .name(account.getName())
                .email(account.getEmail())
                .fpt_member(account.isFpt_member())
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
