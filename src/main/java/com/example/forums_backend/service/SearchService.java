package com.example.forums_backend.service;

import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostService postService;
    @Autowired
    AccountService accountService;

    public List<PostResDto> filterPostByTag(String slug) throws AppException {
        Optional<Tag> tagOptional = tagRepository.findFirstBySlug(slug);
        if(!tagOptional.isPresent()){
            throw new AppException("TAG NOT FOUND");
        }
        Account currentUser = accountService.getUserInfoData();
        Tag tag = tagOptional.get();
        List<Post> postList = postRepository.findByTagsIn((Collection<Tag>) tag, Sort.by(Sort.Direction.DESC,"createdAt"));
        return postList.stream()
                .distinct()
                .map(it -> postService.fromEntityPostDto(it, currentUser))
                .collect(Collectors.toList());
    }

    public void searchByKeyword(String keyword){

    }

}

