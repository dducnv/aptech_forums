package com.example.forums_backend.api;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.service.AccountManagerService;
import com.example.forums_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class WebController {
    @Autowired
    NotificationService notificationService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void home(HttpServletResponse httpServletResponse) throws IOException {
         httpServletResponse.sendRedirect("https://forums-demo.vercel.app/");
    }
    @RequestMapping(value = "/api/user-role", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok("This is user");
    }

    @RequestMapping(value = "/api/admin-role", method = RequestMethod.GET)
    public ResponseEntity<?> getAdmin(){
        return ResponseEntity.ok("This is admin");
    }
    @RequestMapping(value = "/api/notification", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNotification(){
        return ResponseEntity.ok(notificationService.getAllNotification());
    }

}
