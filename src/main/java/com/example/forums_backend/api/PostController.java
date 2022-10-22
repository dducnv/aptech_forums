package com.example.forums_backend.api;

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

import static com.example.forums_backend.config.constant.route.ClientRoute.*;

@RestController
@RequestMapping(PREFIX_CLIENT_ROUTE)
@RequiredArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    VoteService voteService;

    @Autowired
    BookmarkService bookmarkService;

    @RequestMapping(value = POSTS_CLIENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @RequestMapping(value = POST_CLIENT_DETAILS_POST_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getDetails(@PathVariable Long id) throws AppException {
        return ResponseEntity.ok(postService.detailsPost(id));
    }

    @RequestMapping(value = POST_CLIENT_CREATE_POST_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.savePost(postRequestDto));
    }

    @RequestMapping(value = POST_UP_VOTE_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> votePost(@PathVariable Long id, @RequestBody VoteRequestDto voteRequestDto) throws AppException {
        try {
            VoteDto voteDto = new VoteDto();
            voteDto.setSubject_id(id);
            voteDto.setSubject(Subject.POST);
            voteDto.setType(VoteType.getVoteType(voteRequestDto.getType()));
            return ResponseEntity.ok(voteService.vote(voteDto));
        } catch (Exception exception) {
            return ResponseEntity.status(400).body("Hệ thống gặp vấn đề");
        }
    }

    @RequestMapping(value = "/post/{id}/bookmark", method = RequestMethod.POST)
    public ResponseEntity<?> postBookmark(@PathVariable Long id) throws AppException {
        BookmarkReqDto bookmarkReqDto = new BookmarkReqDto();
        bookmarkReqDto.setSubject_id(id);
        bookmarkReqDto.setSubject(Subject.POST);
        return ResponseEntity.status(200).body(bookmarkService.Bookmark(bookmarkReqDto));
    }
}
