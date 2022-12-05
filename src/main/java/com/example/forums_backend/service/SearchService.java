package com.example.forums_backend.service;

import com.example.forums_backend.dto.*;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.entity.TagFollowing;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.AccountRepository;
import com.example.forums_backend.repository.PostRepository;
import com.example.forums_backend.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostService postService;
    @Autowired
    AccountService accountService;
    @Autowired
    TagService tagService;

    public PostsByTagDto filterPostByTag(String slug) throws AppException {
        Optional<Tag> tagOptional = tagRepository.findFirstBySlug(slug);
        if (!tagOptional.isPresent()) {
            throw new AppException("TAG NOT FOUND");
        }
        Account currentUser = accountService.getUserInfoData();
        Tag tag = tagOptional.get();
        TagFollowResDto tagFollowResDto = tagService.fromEntityTagDto(tag, currentUser);
        List<Post> postList = postRepository.findByTagsIn(Collections.singleton(tag), Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostResDto> postResDtoList = postList.stream()
                .distinct()
                .map(it -> postService.fromEntityPostDto(it, currentUser))
                .collect(Collectors.toList());
        return PostsByTagDto.builder()
                .tag_details(tagFollowResDto)
                .posts(postResDtoList)
                .build();
    }

    public List<?> searchByKeyword(String keyword, String type, int limit) {
        if (Objects.equals(type, "post")) {
            return searchPost(keyword, limit);
        } else if (Objects.equals(type, "account")) {
            return searchAcc(keyword);
        } else if (Objects.equals(type, "tag")) {
            return searchTag(keyword);
        }
        return null;
    }

    public List<PostSearchDto> searchPost(String keyword, int limit) {
        List<Post> postListSearch = postRepository.searchAllPopular(keyword);
        return postListSearch.stream().distinct().limit(limit).map(this::fromTagFollowingToTag).collect(Collectors.toList());
    }

    public List<Account> searchAcc(String keyword) {
        List<Account> accountList = new ArrayList<>();
        if (keyword.startsWith("@")) {
            String key = keyword.replace("@", "").trim();
            accountList = accountRepository.searchAccountbyUserName(key);
        }
        else {
            accountList = accountRepository.searchAccount(keyword);
        }
        return accountList;
    }

    public List<Tag> searchTag(String keyword) {
        List<Tag> tagList = tagRepository.searchTag(keyword);
        return tagList;
    }


    public PostSearchDto fromTagFollowingToTag(Post post) {
        PostSearchDto postSearchDto = new PostSearchDto();
        postSearchDto.setTitle(post.getTitle());
        postSearchDto.setSlug(post.getSlug());
        postSearchDto.setComment_count(post.getComment().size());
        postSearchDto.setVote_count(post.getVoteCount());
        postSearchDto.setBookmark_count(post.getBookmarks().size());
        postSearchDto.setCreatedAt(post.getCreatedAt());
        return postSearchDto;
    }

    public AccountSearchDto fromTagFollowingToTag(Account account) {
        AccountSearchDto accountSearchDto = new AccountSearchDto();
        accountSearchDto.setName(account.getName());
        return accountSearchDto;
    }
}

