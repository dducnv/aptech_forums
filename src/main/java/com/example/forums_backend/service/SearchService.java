package com.example.forums_backend.service;

import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.entity.my_enum.SortPost;
import com.example.forums_backend.repository.PostRepository;
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
public class SearchService {
    @Autowired
    PostRepository postRepository;
}

