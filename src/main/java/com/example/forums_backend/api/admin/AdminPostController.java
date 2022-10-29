package com.example.forums_backend.api.admin;

import com.example.forums_backend.dto.BookmarkReqDto;
import com.example.forums_backend.dto.PostRequestDto;
import com.example.forums_backend.dto.VoteDto;
import com.example.forums_backend.dto.VoteRequestDto;
import com.example.forums_backend.entity.my_enum.Subject;
import com.example.forums_backend.entity.my_enum.VoteType;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.service.BookmarkService;
import com.example.forums_backend.service.PostService;
import com.example.forums_backend.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forums_backend.config.constant.route.AdminRoute.*;

@RestController
@RequestMapping(PREFIX_ADMIN_ROUTE)
@RequiredArgsConstructor
public class AdminPostController {
    @Autowired
    PostService postService;

    @RequestMapping(value = POST_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

//    @RequestMapping(value = POST_PATH, method = RequestMethod.GET)
//    public ResponseEntity<?> getDetails(@RequestParam("slug") String slug) throws AppException {
//        if(slug.isEmpty()){
//            return ResponseEntity.status(404).body("Param not found");
//        }
//        return ResponseEntity.ok(postService.detailsPost(slug));
//    }

    @RequestMapping(value = POST_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.savePost(postRequestDto));
    }
}