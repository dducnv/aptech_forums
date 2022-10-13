package com.example.forums_backend.service;

import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.dto.PostRequestDto;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountService accountService;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public boolean savePost(PostRequestDto postRequestDto){
        Account author = accountService.getUserInfoData();
        Post postSave = new Post();
        postSave.setTitle(postRequestDto.getTitle());
        postSave.setContent(postRequestDto.getContent());
        postSave.setTags(postRequestDto.getTags());
        postSave.setAuthor(author);
        postRepository.save(postSave);
        return true;
    }

    public Post findByID(Long id) throws AppException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){
            throw new AppException("POST NOT FOUND!");
        }
        return optionalPost.get();
    }

    public PostResDto detailsPost(Long id) throws AppException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){
            throw new AppException("POST NOT FOUND!");
        }
        Post resultPost = optionalPost.get();
        PostResDto postResDto = new PostResDto();
        postResDto.setTitle(resultPost.getTitle());
        postResDto.setContent(resultPost.getContent());
        postResDto.setAccount(postResDto.getAccount());
        postResDto.setVoteCount(resultPost.getVoting().size());
        postResDto.setVote(false);
        postResDto.setVoteType(0);
        return postResDto;
    }
}
