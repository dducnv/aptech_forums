package com.example.forums_backend.api;

import com.example.forums_backend.entity.dto.RegisterDto;
import com.example.forums_backend.exception.AccountExistException;
import com.example.forums_backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    final AccountService accountService;
    @RequestMapping(value = "/register", produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<RegisterDto> register(@RequestBody @Valid RegisterDto registerDto) throws AccountExistException {
        return ResponseEntity.ok(accountService.register(registerDto));
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> userInfo(){
        return ResponseEntity.ok(accountService.getUserInfo());
    }

}
