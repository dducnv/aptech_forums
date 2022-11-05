package com.example.forums_backend.api;

import com.example.forums_backend.dto.TagFollowReqDto;
import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.example.forums_backend.config.constant.route.ClientRoute.*;

@RestController
@RequestMapping(PREFIX_CLIENT_ROUTE)
@RequiredArgsConstructor
public class TagController {
    @Autowired
    TagService tagService;
    @RequestMapping(value = TAG_CLIENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(tagService.findAll());
    }
    @RequestMapping(value = TAG_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> createTag(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.save(tag));
    }
    @RequestMapping(value = TAG_FOLLOW_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> followTag(@RequestBody TagFollowReqDto tagFollowReqDto) throws AppException {
        return ResponseEntity.ok(tagService.followTag(tagFollowReqDto));
    }
    @RequestMapping(value = MY_TAG_FOLLOWING, method = RequestMethod.GET)

    public ResponseEntity<?> myTagFollowing(){
        return ResponseEntity.ok(tagService.myTagFollowing());
    }
}
