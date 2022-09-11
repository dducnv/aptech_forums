package com.example.forums_backend.api;

import com.example.forums_backend.entity.dto.*;
import com.example.forums_backend.exception.AccountException;
import com.example.forums_backend.service.AccountService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.forums_backend.config.route.constant.AuthRoute.*;

@RestController
@RequestMapping(PREFIX_AUTH_ROUTE)
@RequiredArgsConstructor
public class AuthController {
    final AccountService accountService;

    @RequestMapping(value = GET_OTP_PATH,produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<CheckAccount> getOtp(@RequestBody LoginEmailDto loginEmailDto){
        CheckAccount checkAccount = accountService.getOtp(loginEmailDto);
        return ResponseEntity.ok(checkAccount);
    }

    @RequestMapping(value = LOGIN_WITH_EMAIL_PATH,produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<CredentialDto> loginWithEmailOtp(@RequestBody @Valid LoginDto loginDto){
        CredentialDto credentialDto;
        try {
            credentialDto = accountService.loginWithOTP(loginDto);
        } catch (AccountException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(credentialDto);
    }
    @RequestMapping(value = REGISTER_PATH, produces = "application/json", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<RegisterDto> register(@RequestBody @Valid RegisterDto registerDto) throws AccountException {
        return ResponseEntity.ok(accountService.register(registerDto));
    }

    @RequestMapping(value = USER_INFO_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> userInfo(){
        return ResponseEntity.ok(accountService.getUserInfo());
    }

}
