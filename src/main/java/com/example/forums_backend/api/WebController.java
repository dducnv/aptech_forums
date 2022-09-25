package com.example.forums_backend.api;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.service.AccountManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WebController {

    @RequestMapping(value = "/user-role", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok("This is user");
    }

    @RequestMapping(value = "/admin-role", method = RequestMethod.GET)
    public ResponseEntity<?> getAdmin(){
        return ResponseEntity.ok("This is admin");
    }

    @RequestMapping(value = "/upload-image", method = RequestMethod.POST)
    public ResponseEntity<?> uploadImage(){
        return ResponseEntity.ok("it uploaded");
    }

}
