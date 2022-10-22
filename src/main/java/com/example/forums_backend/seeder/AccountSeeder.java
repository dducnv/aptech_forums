package com.example.forums_backend.seeder;

import com.example.forums_backend.dto.RegisterDto;
import com.example.forums_backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountSeeder implements CommandLineRunner {
    List<RegisterDto> registerDtoList = new ArrayList<>();
    @Autowired
    AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        RegisterDto registerDtoUser1 = new RegisterDto("Nguyen Van Duc","ducbe2k2@gmail.com");
        RegisterDto registerDtoUser2 = new RegisterDto("Tran Viet Cuong","cuong@gmail.com");
        accountService.register(registerDtoUser1);
        accountService.register(registerDtoUser2);

    }
}
