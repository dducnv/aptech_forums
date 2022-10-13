package com.example.forums_backend.api;

import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.forums_backend.config.route.constant.ClientRoute.*;

@RestController
@RequestMapping(PREFIX_CLIENT_ROUTE)
@RequiredArgsConstructor
public class TagController {
    @Autowired
    TagService tagService;
    @RequestMapping(value = TAG_CLIENT_PATH, method = RequestMethod.GET)
    public ResponseEntity<List<Tag>> getAll(){
        return ResponseEntity.ok(tagService.findAll());
    }
    @RequestMapping(value = TAG_CLIENT_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> createTag(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.save(tag));
    }
}
