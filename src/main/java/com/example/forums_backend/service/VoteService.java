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
            voteResDto.setVoteType(comment.getVoteType());
            voteResDto.setVote_count(comment.getVoteCount());
        }
        return voteResDto;
    }

    public PostResDto postVote(Long postId, VoteType type, Account account) throws AppException {
        Post post = postService.findByID(postId);
        Optional<Voting> votingOptional = voteRepository.findFirstByPost_IdAndAccount_Id(post.getId(), account.getId());
        if(!votingOptional.isPresent()){
            Voting voteSave = new Voting();
            voteSave.setPost(post);
            voteSave.setAccount(account);
            if(type == VoteType.UPVOTE){
                voteSave.setType(VoteType.UPVOTE);
                post.setVote_count(post.getVote_count() + 1);
            }else if(type == VoteType.DOWN_VOTE){
                voteSave.setType(VoteType.DOWN_VOTE);
                post.setVote_count(post.getVote_count() - 1);
            }
            voteRepository.save(voteSave);
            postRepository.save(post);
        }else {
            Voting voteExist = votingOptional.get();
            if(type == VoteType.UPVOTE){
                if(voteExist.getType() == VoteType.UPVOTE){
                    delete(voteExist.getId());
                    post.setVote_count(post.getVote_count() - 1);
                }else if(voteExist.getType() == VoteType.DOWN_VOTE){
                    voteExist.setType(VoteType.UPVOTE);
                    post.setVote_count(upVotePostCount(post) + 1);
                    voteRepository.save(voteExist);
                }
            } else if (type == VoteType.DOWN_VOTE) {
                if(voteExist.getType() == VoteType.DOWN_VOTE){
                    delete(voteExist.getId());
                    post.setVote_count(post.getVote_count() + 1);
                }else if(voteExist.getType() == VoteType.UPVOTE){
                    voteExist.setType(VoteType.DOWN_VOTE);
                    post.setVote_count(upVotePostCount(post) - 1);
                    voteRepository.save(voteExist);
                }
            }

            postRepository.save(post);
        }
        return postService.detailsPost(post.getSlug());
    }

    public CommentResDto commentVote(Long commentId, VoteType type, Account account) throws AppException {
        Comment comment = commentService.findById(commentId);
        Optional<Voting> votingOptional = voteRepository.findFirstByComment_IdAndAccount_Id(comment.getId(), account.getId());
        if(!votingOptional.isPresent()){
            Voting voteSave = new Voting();
            voteSave.setComment(comment);
            voteSave.setAccount(account);
            if(type == VoteType.UPVOTE){
                voteSave.setType(VoteType.UPVOTE);
                comment.setVote_count(comment.getVote_count() + 1);
            }else if(type == VoteType.DOWN_VOTE){
                voteSave.setType(VoteType.DOWN_VOTE);
                comment.setVote_count(comment.getVote_count() - 1);
            }
            voteRepository.save(voteSave);
            commentRepository.save(comment);
        }else {
            Voting voteExist = votingOptional.get();
            if(type == VoteType.UPVOTE){
                if(voteExist.getType() == VoteType.UPVOTE){
                    delete(voteExist.getId());
                    comment.setVote_count(comment.getVote_count() - 1);
                }else if(voteExist.getType() == VoteType.DOWN_VOTE){
                    voteExist.setType(VoteType.UPVOTE);
                    comment.setVote_count(upVoteCommentCount(comment) - 1);
                    voteRepository.save(voteExist);
                }
            } else if (type == VoteType.DOWN_VOTE) {
                if(voteExist.getType() == VoteType.DOWN_VOTE){
                    delete(voteExist.getId());
                    comment.setVote_count(comment.getVote_count() + 1);
                }else if(voteExist.getType() == VoteType.UPVOTE){
                    voteExist.setType(VoteType.DOWN_VOTE);
                    comment.setVote_count(upVoteCommentCount(comment) - 1);
                    voteRepository.save(voteExist);
                }
            }

            commentRepository.save(comment);
        }
        return commentService.fromEntityCommentDto(comment,account);
    }


    public int upVotePostCount(Post post){
        List<Voting> upVote = findVotingPostByType(VoteType.UPVOTE, Subject.POST,post.getId());
        return upVote.size();
    }

    public int upVoteCommentCount(Comment comment){
        List<Voting> upVote = findVotingCommentByType(VoteType.UPVOTE, Subject.COMMENT,comment.getId());
        return upVote.size();
    }

    public void delete(Long id) {
        voteRepository.deleteById(id);
    }

    public List<Voting> findVotingPostByType(VoteType voteType, Subject subject, Long postId) {
        return voteRepository.findVotingByTypeAndSubjectAndPost_Id(voteType, subject,postId);
    }
    public List<Voting> findVotingCommentByType(VoteType voteType, Subject subject, Long postId) {
        return voteRepository.findVotingByTypeAndSubjectAndComment_Id(voteType, subject,postId);
    }

}
