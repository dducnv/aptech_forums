package com.example.forums_backend.service;

import com.example.forums_backend.dto.CommentResDto;
import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.dto.VoteResDto;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Comment;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.entity.Voting;
import com.example.forums_backend.dto.VoteDto;
import com.example.forums_backend.entity.my_enum.Subject;
import com.example.forums_backend.entity.my_enum.VoteType;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.CommentRepository;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.VoteRepository;
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
public class VoteService {
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;

    public VoteResDto vote(VoteDto voteDto) throws AppException {
        Account author = accountService.getUserInfoData();
        VoteResDto voteResDto = new VoteResDto();
        if (voteDto.getSubject() == Subject.POST) {
            PostResDto post = postVote(voteDto.getSubject_id(), voteDto.getType(), author);
            voteResDto.setVoteType(post.getVoteType());
            voteResDto.setVote_count(post.getVoteCount());
        } else if (voteDto.getSubject() == Subject.COMMENT) {
            CommentResDto comment = commentVote(voteDto.getSubject_id(), voteDto.getType(), author);
            voteResDto.setVoteType(voteDto.getType());
            voteResDto.setVote_count(comment.getVoteCount());
        }
        return voteResDto;
    }

    public PostResDto postVote(Long postId, VoteType type, Account account) throws AppException {
        Post post = postService.findByID(postId);
        Optional<Voting> votingOptional = voteRepository.findFirstByPost_IdAndAccount_Id(post.getId(), account.getId());
        Voting voteSave = new Voting();
        List<Voting> upVote = findVotingByType(VoteType.UPVOTE, Subject.POST);
        if (votingOptional.isPresent()) {
            Voting voting = votingOptional.get();
            if (type == VoteType.UPVOTE) {
                if (voting.getType() == VoteType.DOWN_VOTE) {
                    voting.setType(VoteType.UPVOTE);
                    post.setVote_count(upVote.size() + 1);
                    voteRepository.save(voting);
                } else if (voting.getType() == VoteType.UPVOTE) {
                    delete(voting.getId());
                    post.setVote_count(upVote.size() - 1);
                }
            } else if (type == VoteType.DOWN_VOTE) {
                if (voting.getType() == VoteType.UPVOTE) {
                    voting.setType(VoteType.DOWN_VOTE);
                    post.setVote_count(upVote.size() - 1);
                    voteRepository.save(voting);
                } else if (voting.getType() == VoteType.DOWN_VOTE) {
                    delete(voting.getId());
                    post.setVote_count(upVote.size() + 1);
                }
            }
            postRepository.save(post);
        } else {
            voteSave.setAccount(account);
            voteSave.setSubject(Subject.POST);
            voteSave.setPost(post);
            if (type == VoteType.UPVOTE) {
                voteSave.setType(VoteType.UPVOTE);
                post.setVote_count(upVote.size() + 1);
            } else if (type == VoteType.DOWN_VOTE) {
                voteSave.setType(VoteType.DOWN_VOTE);
                post.setVote_count(upVote.size() - 1);
            }
            voteRepository.save(voteSave);
            postRepository.save(post);
        }
        return postService.detailsPost(post.getSlug());
    }

    public CommentResDto commentVote(Long commentId, VoteType type, Account account) throws AppException {
        Comment comment = commentService.findById(commentId);
        Optional<Voting> votingOptional = voteRepository.findFirstByComment_IdAndAccount_Id(comment.getId(), account.getId());
        Voting voteSave = new Voting();
        List<Voting> upVote = findVotingByType(VoteType.UPVOTE, Subject.COMMENT);
        if (votingOptional.isPresent()) {
            Voting voting = votingOptional.get();
            if (type == VoteType.UPVOTE) {
                if (voting.getType() == VoteType.DOWN_VOTE) {
                    voting.setType(VoteType.UPVOTE);
                    comment.setVote_count(upVote.size() + 1);
                    voteRepository.save(voting);
                } else if (voting.getType() == VoteType.UPVOTE) {
                    delete(voting.getId());
                    comment.setVote_count(upVote.size() - 1);
                }
            } else if (type == VoteType.DOWN_VOTE) {
                if (voting.getType() == VoteType.UPVOTE) {
                    voting.setType(VoteType.DOWN_VOTE);
                    comment.setVote_count(upVote.size() - 1);
                    voteRepository.save(voting);
                } else if (voting.getType() == VoteType.DOWN_VOTE) {
                    delete(voting.getId());
                    comment.setVote_count(upVote.size() + 1);
                }
            }
            commentRepository.save(comment);
        } else {
            voteSave.setAccount(account);
            voteSave.setSubject(Subject.COMMENT);
            voteSave.setComment(comment);
            if (type == VoteType.UPVOTE) {
                voteSave.setType(VoteType.UPVOTE);
                comment.setVote_count(upVote.size() + 1);
            } else if (type == VoteType.DOWN_VOTE) {
                voteSave.setType(VoteType.DOWN_VOTE);
                comment.setVote_count(upVote.size() - 1);
            }
            voteRepository.save(voteSave);
            commentRepository.save(comment);
        }
        return commentService.fromEntityCommentDto(comment,account);
    }

    public void delete(Long id) {
        voteRepository.deleteById(id);
    }

    public List<Voting> findVotingByType(VoteType voteType, Subject subject) {
        return voteRepository.findVotingByTypeAndSubject(voteType, subject);
    }

}
