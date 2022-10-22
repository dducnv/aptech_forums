package com.example.forums_backend.service;

import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Bookmark;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.dto.PostRequestDto;
import com.example.forums_backend.entity.Voting;
import com.example.forums_backend.entity.my_enum.VoteType;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.BookmarkRepository;
import com.example.forums_backend.repository.VoteRepository;
import com.example.forums_backend.utils.SlugGenerating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {
    PostRepository postRepository;

    AccountService accountService;

    VoteRepository voteRepository;
    BookmarkRepository bookmarkRepository;

    @Autowired
    public PostService(PostRepository postRepository, AccountService accountService, VoteRepository voteRepository, BookmarkRepository bookmarkRepository) {
        this.postRepository = postRepository;
        this.accountService = accountService;
        this.voteRepository = voteRepository;
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<PostResDto> findAll() {
        Account currentUser = accountService.getUserInfoData();
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(it -> fromEntityPostDto(it, currentUser)).collect(Collectors.toList());
    }

    public boolean savePost(PostRequestDto postRequestDto) {
        Account author = accountService.getUserInfoData();
        Post postSave = new Post();
        String slugGenerate = SlugGenerating.toSlug(postRequestDto.getTitle()).concat("-"+System.currentTimeMillis());
        postSave.setTitle(postRequestDto.getTitle());
        postSave.setSlug(slugGenerate);
        postSave.setContent(postRequestDto.getContent());
        postSave.setTags(postRequestDto.getTags());
        postSave.setAuthor(author);
        postRepository.save(postSave);
        return true;
    }

    public PostResDto detailsPost(Long id) throws AppException {
        try {
            Account currentUser = accountService.getUserInfoData();
            Optional<Post> optionalPost = postRepository.findById(id);
            if (!optionalPost.isPresent()) {
                throw new AppException("POST NOT FOUND!");
            }
            Post resultPost = optionalPost.get();
            return fromEntityPostDto(resultPost, currentUser);
        } catch (Exception exception) {
            log.info(exception.getMessage());
        }
        return null;
    }

    public Post findByID(Long id) throws AppException {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            throw new AppException("POST NOT FOUND!");
        }
        return optionalPost.get();
    }

    public PostResDto fromEntityPostDto(Post post, Account currentUser) {
        Voting voting = null;
        Bookmark bookmark = null;
        PostResDto postResDto = new PostResDto();
        if(currentUser !=null){
            voting = voteRepository.findFirstByPost_IdAndAccount_Id(post.getId(), currentUser.getId()).orElse(null);
            bookmark = bookmarkRepository.findFirstByPost_IdAndAccount_Id(post.getId(),currentUser.getId()).orElse(null);
        }
        postResDto.setId(post.getId());
        postResDto.setTitle(post.getTitle());
        postResDto.setSlug(post.getSlug());
        postResDto.setContent(post.getContent());
        postResDto.setAccount(post.getAuthor());
        postResDto.setVoteCount(post.getVote_count());
        postResDto.setTags(post.getTags());
        postResDto.setCommentCount(post.getComment().size());
        postResDto.setBookmarkCount(post.getBookmarks().size());
        postResDto.setVote(voting != null);
        postResDto.setVoteType(voting == null ? VoteType.UNDEFINED : voting.getType());
        postResDto.setBookmark(bookmark != null);
        postResDto.setCreatedAt(post.getCreatedAt());
        return postResDto;
    }
}
