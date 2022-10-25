package com.example.forums_backend.service;

import com.example.forums_backend.dto.CommentReqDto;
import com.example.forums_backend.dto.CommentResDto;
import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.entity.*;
import com.example.forums_backend.entity.my_enum.VoteType;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.BookmarkRepository;
import com.example.forums_backend.repository.CommentRepository;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
//@Transactional
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    PostService postService;
    @Autowired
    BookmarkRepository bookmarkRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

//    @Transactional(rollbackFor = AppException.class)
    public CommentResDto saveComment(Long postId, CommentReqDto commentReqDto) {
        try {
            Account account = accountService.getUserInfoData();
            Post post = postService.findByID(postId);
            Comment comment = new Comment();
            comment.setAccount(account);
            comment.setContent(commentReqDto.getContent());
            comment.setPost(post);
            commentRepository.save(comment);
            if (commentReqDto.getReply_to() != null) {
                Comment findComment = findById(commentReqDto.getReply_to().getId());
                Set<Comment> commentSet = new HashSet<>();
                commentSet.add(comment);
                findComment.setReply_to(commentSet);
                commentRepository.save(findComment);
            }
            return fromEntityCommentDto(comment,account);
        } catch (Exception exception) {
            log.info("Comment error: " + exception.getMessage());
            throw new RuntimeException();
        }
    }

    public Comment findById(Long id) throws AppException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (!optionalComment.isPresent()) {
            throw new AppException("COMMENT NOT FOUND!");
        }
        return optionalComment.get();
    }

    public List<CommentResDto> findCommentByPost_Id(Long postId) {
        Account currentUser = accountService.getUserInfoData();
        List<Comment> commentList = commentRepository.findCommentByPost_Id(postId);
        return commentList.stream().map(it -> fromEntityCommentDto(it, currentUser)).collect(Collectors.toList());
    }

    public CommentResDto fromEntityCommentDto(Comment comment, Account currentUser) {
        Voting voting = null;
        Bookmark bookmark = null;
        CommentResDto commentResDto = new CommentResDto();
        if (currentUser != null) {
            voting = voteRepository.findFirstByComment_IdAndAccount_Id(comment.getId(), currentUser.getId()).orElse(null);
            bookmark = bookmarkRepository.findFirstByComment_IdAndAccount_Id(comment.getId(), currentUser.getId()).orElse(null);
        }
        List<CommentResDto> replyComment = comment.getReply_to().stream().map(it -> fromEntityCommentDto(it, currentUser)).collect(Collectors.toList());
        commentResDto.setId(comment.getId());
        commentResDto.setAccount(comment.getAccount());
        commentResDto.setContent(comment.getContent());
        commentResDto.setVoteCount(comment.getVote_count());
        commentResDto.setReply(replyComment);
        commentResDto.setVote(voting != null);
        commentResDto.setBookmark(bookmark != null);
        commentResDto.setVoteType(voting == null ? VoteType.UNDEFINED : voting.getType());
        return commentResDto;
    }
}
