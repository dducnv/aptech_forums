package com.example.forums_backend.service;

import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.dto.PostSearchDto;
import com.example.forums_backend.dto.PostsByTagDto;
import com.example.forums_backend.dto.TagFollowResDto;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.entity.TagFollowing;
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
import java.util.Collections;
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
    @Autowired
    TagService tagService;

    public PostsByTagDto filterPostByTag(String slug) throws AppException {
        Optional<Tag> tagOptional = tagRepository.findFirstBySlug(slug);
        if(!tagOptional.isPresent()){
            throw new AppException("TAG NOT FOUND");
        }
        Account currentUser = accountService.getUserInfoData();
        Tag  tag = tagOptional.get();
        TagFollowResDto tagFollowResDto = tagService.fromEntityTagDto(tag,currentUser);
        List<Post> postList = postRepository.findByTagsIn(Collections.singleton(tag), Sort.by(Sort.Direction.DESC,"createdAt"));
        List<PostResDto> postResDtoList =  postList.stream()
                .distinct()
                .map(it -> postService.fromEntityPostDto(it, currentUser))
                .collect(Collectors.toList());
        return PostsByTagDto.builder()
                .tag_details(tagFollowResDto)
                .posts(postResDtoList)
                .build();
    }

    public List<PostSearchDto> searchByKeyword(String keyword,int limit){
        List<Post> postListSearch = postRepository.searchAllPopular(keyword);
        return postListSearch.stream().distinct().limit(limit).map(this::fromTagFollowingToTag).collect(Collectors.toList());
    }


    public PostSearchDto fromTagFollowingToTag(Post post){
        PostSearchDto postSearchDto = new PostSearchDto();
        postSearchDto.setTitle(post.getTitle());
        postSearchDto.setSlug(post.getSlug());
        postSearchDto.setComment_count(post.getComment().size());
        postSearchDto.setVote_count(post.getVoteCount());
        postSearchDto.setBookmark_count(post.getBookmarks().size());
        return postSearchDto;
    }
}

