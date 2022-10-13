package com.example.forums_backend.api;

import com.example.forums_backend.dto.PostRequestDto;
import com.example.forums_backend.dto.VoteDto;
import com.example.forums_backend.dto.VoteRequestDto;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.service.PostService;
import com.example.forums_backend.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forums_backend.config.route.constant.ClientRoute.*;

@RestController
@RequestMapping(PREFIX_CLIENT_ROUTE)
@RequiredArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    VoteService voteService;

    @RequestMapping(value = POST_CLIENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getDetails(@PathVariable Long id) throws AppException {
        return ResponseEntity.ok(postService.detailsPost(id));
    }

    @RequestMapping(value = POST_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.savePost(postRequestDto));
    }

    @RequestMapping(value = POST_UP_VOTE_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> upVote(@PathVariable Long id, @RequestBody VoteRequestDto voteRequestDto) throws AppException {
        VoteDto voteDto = new VoteDto();
        voteDto.setSubject_id(id);
        voteDto.setSubject("POST");
        return ResponseEntity.ok(voteService.upVote(voteDto));
    }
    @RequestMapping(value = POST_DOWN_VOTE_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> downVote(@PathVariable Long id, @RequestBody VoteRequestDto voteRequestDto) throws AppException {
        VoteDto voteDto = new VoteDto();
        voteDto.setSubject_id(id);
        voteDto.setSubject("POST");
        return ResponseEntity.ok(voteService.downVote(voteDto));
    }
}
