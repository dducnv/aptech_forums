package com.example.forums_backend.api;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.service.AccountManagerService;
import com.example.forums_backend.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class WebController {
    final AccountManagerService accountManagerService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(accountManagerService.findAll());
    }
}
