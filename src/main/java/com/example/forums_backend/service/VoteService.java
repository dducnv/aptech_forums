package com.example.forums_backend.service;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.entity.Voting;
import com.example.forums_backend.dto.VoteDto;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class VoteService {
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountService accountService;

    @Autowired
    PostService postService;

    public Post upVote(VoteDto voteDto) throws AppException {
        Account author = accountService.getUserInfoData();
        Voting voteSave = new Voting();
        if(voteDto.getSubject().equals("POST")){
            Post post = postService.findByID(voteDto.getSubject_id());
            Optional<Voting> votingOptional = voteRepository.findFirstByPost_IdAndAccount_Id(post.getId(),author.getId());
            if(votingOptional.isPresent()){
                Voting voting = votingOptional.get();
                if(voting.getType() == -1){
                    voting.setType(1);
                    voteRepository.save(voting);
                }else {
                    voteRepository.deleteById(voting.getId());
                }

            }else {
                voteSave.setAccount(author);
                voteSave.setSubject(voteDto.getSubject());
                voteSave.setType(1);
                voteSave.setPost(post);
                voteRepository.save(voteSave);
                post.setVote_count(post.getVote_count() + -1);
                postRepository.save(post);
            }
            return post;
        }

        return null;
    };

    public Post downVote(VoteDto voteDto) throws AppException {
        Account author = accountService.getUserInfoData();
        Voting voteSave = new Voting();
        if(voteDto.getSubject().equals("POST")){
            Post post = postService.findByID(voteDto.getSubject_id());
            Optional<Voting> votingOptional = voteRepository.findFirstByPost_IdAndAccount_Id(post.getId(),author.getId());
            if(votingOptional.isPresent()){
                Voting voting = votingOptional.get();
                if(voting.getType() == 1){
                    voting.setType(-1);
                    voteRepository.save(voting);
                }else {
                    voteRepository.deleteById(voting.getId());
                }
            }else {
                voteSave.setAccount(author);
                voteSave.setSubject(voteDto.getSubject());
                voteSave.setType(-1);
                voteSave.setPost(post);
                voteRepository.save(voteSave);
                post.setVote_count(post.getVote_count() + -1);
                postRepository.save(post);
            }

            return post;
        }

        return null;
    };


}
