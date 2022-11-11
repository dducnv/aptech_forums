package com.example.forums_backend.api;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.UserContact;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.forums_backend.config.constant.route.ClientRoute.*;

@RestController
@RequiredArgsConstructor
public class WebController {
    @Autowired
    NotificationService notificationService;
    @Autowired
    BookmarkService bookmarkService;
    @Autowired
    AccountService accountService;
    @Autowired
    SearchService searchService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void home(HttpServletResponse httpServletResponse) throws IOException {
         httpServletResponse.sendRedirect("https://forums-demo.vercel.app/");
    }
    @RequestMapping(value = "/api/notifications", method = RequestMethod.GET)
    public ResponseEntity<?> getAllNotification(){
        return ResponseEntity.ok(notificationService.getAllNotification());
    }

    @RequestMapping(value = "/api/{id}/send-notification", method = RequestMethod.GET)
    public ResponseEntity<?> sendNotify(@PathVariable Long id) throws AppException {
        return ResponseEntity.ok(notificationService.seenNotification(id));
    }
    @RequestMapping(value = "/api/my-bookmarks", method = RequestMethod.GET)
    public ResponseEntity<?> getBookmarks(){
        return ResponseEntity.ok(bookmarkService.BookmarkList());
    }
    @RequestMapping(value = MY_CONTACT_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getMyContact(){
        return ResponseEntity.ok(accountService.getUserContact());
    }
    @RequestMapping(value = USER_CONTACT_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getUserContact(@PathVariable String username){
        return ResponseEntity.ok(accountService.getUserContactByUsername(username));
    }
    @RequestMapping(value = MY_BADGE_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getMyBadges(){
        return ResponseEntity.ok(accountService.getListBadge());
    }
    @RequestMapping(value = USER_BADGE_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getMyUserBadges(@PathVariable String username){
        return ResponseEntity.ok(accountService.getListBadgeByUsername(username));
    }
    @RequestMapping(value = "/api/{id}/delete-contact", method = RequestMethod.GET)
    public ResponseEntity<?> deleteContact(@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteContact(id));
    }
    @RequestMapping(value = "/api/{id}/update-contact", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContact(@PathVariable Long id, @RequestBody UserContact userContact){
        return ResponseEntity.ok(accountService.updateContact(id,userContact));
    }
    @RequestMapping(value = "/api/filter/{slug}/posts-by-tag")
    public ResponseEntity<?> postsByTag(@PathVariable String slug) throws AppException {
        return ResponseEntity.ok(searchService.filterPostByTag(slug));
    }
}
