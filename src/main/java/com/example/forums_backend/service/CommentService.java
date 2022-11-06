package com.example.forums_backend.service;

import com.example.forums_backend.dto.CommentReqDto;
import com.example.forums_backend.dto.CommentResDto;
import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.entity.*;
import com.example.forums_backend.entity.my_enum.NotificationType;
import com.example.forums_backend.entity.my_enum.StatusEnum;
import com.example.forums_backend.entity.my_enum.VoteType;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.BookmarkRepository;
import com.example.forums_backend.repository.CommentRepository;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    NotificationService notificationService;
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

    public List<CommentResDto> myComments() {
        Account currentUser = accountService.getUserInfoData();
        List<Comment> commentList = commentRepository.findCommentByAccount_Id(currentUser.getId());
        return commentList.stream().map(it -> fromEntityCommentDto(it, currentUser)).collect(Collectors.toList());
    }

    public List<CommentResDto> userComments(String username){
        Account currentUser = accountService.getUserInfoData();
        Account account = accountService.findByUsername(username);
        List<Comment> commentList = commentRepository.findCommentByAccount_Id(account.getId());
        return commentList.stream().map(it -> fromEntityCommentDto(it, currentUser)).collect(Collectors.toList());
    }

    //    @Transactional(rollbackFor = AppException.class)
    public CommentResDto saveComment(Long postId, CommentReqDto commentReqDto) {
        try {
            Account account = accountService.getUserInfoData();
            Post post = postService.findByID(postId);
            Comment comment = new Comment();
            Notification notification = new Notification();
            notification.setReceiver(post.getAuthor());
            notification.setInteractive_user(account);
            notification.setType(NotificationType.COMMENT);
            if (commentReqDto.getReply_to() != null) {
                notification.setType(NotificationType.REPLY_COMMENT);
                Comment findComment = findById(commentReqDto.getReply_to().getId());
                comment.setParent(findComment);
                notification.setRedirect_url("/binh-luan/".concat(findComment.getId().toString()));
            }
            comment.setAccount(account);
            comment.setContent(commentReqDto.getContent());
            comment.setPost(post);
            comment.setStatus(StatusEnum.ACTIVE);
            notificationService.saveNotification(notification);
            commentRepository.save(comment);
            return fromEntityCommentDto(comment, account);
        } catch (Exception exception) {
            log.info("Comment error: " + exception.getMessage());
            throw new RuntimeException();
        }
    }

    public CommentResDto findByIdToDto(Long id) throws AppException {
        Account account = accountService.getUserInfoData();
        Comment comment = findById(id);
        return fromEntityCommentDto(comment, account);
    }
    public CommentResDto updateMyComment(Long commentId, CommentReqDto commentReqDto) throws AppException{
        Account currentUser = accountService.getUserInfoData();
        Comment comment = findById(commentId);
        if (comment.getAccount().equals(currentUser)){
            comment.setContent(commentReqDto.getContent());
            commentRepository.save(comment);
            return fromEntityCommentDto(comment,currentUser);
        }
        return null;
    }
    public boolean deleteMyComment(Long commentId) throws AppException {
        Account currentUser = accountService.getUserInfoData();
        Comment comment = findById(commentId);
        if(comment.getAccount().equals(currentUser)){
            commentRepository.deleteById(comment.getId());
            return true;
        }
        return false;
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
        Sort sort = Sort.by(
                Sort.Order.desc("createdAt"),
                Sort.Order.desc("voteCount"));
        List<Comment> commentList = commentRepository.findByPost_Id(postId,sort);
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
        commentResDto.setVoteCount(comment.getVoteCount() );
        commentResDto.setReply(replyComment);
        commentResDto.setChildren(comment.getParent() != null);
        commentResDto.setVote(voting != null);
        commentResDto.setBookmark(bookmark != null);
        commentResDto.setMyComment(comment.getAccount() == currentUser);
        commentResDto.setVoteType(voting == null ? VoteType.UNDEFINED : voting.getType());
        commentResDto.setCreatedAt(comment.getCreatedAt());
        return commentResDto;
    }
}
