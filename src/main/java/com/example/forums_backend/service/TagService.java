package com.example.forums_backend.service;

import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TagService {
    @Autowired
    TagRepository tagRepository;


    public List<Tag> findAll(){
        return  tagRepository.findAll();
    }
    public Tag save(Tag tag){
        return  tagRepository.save(tag);
    }
}
