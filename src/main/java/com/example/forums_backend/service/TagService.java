package com.example.forums_backend.service;

import com.example.forums_backend.dto.PostResDto;
import com.example.forums_backend.dto.TagFollowReqDto;
import com.example.forums_backend.dto.TagFollowResDto;
import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Post;
import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.entity.TagFollowing;
import com.example.forums_backend.exception.AppException;
import com.example.forums_backend.repository.TagFollowingRepository;
import com.example.forums_backend.repository.TagRepository;
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
public class TagService {
    @Autowired
    AccountService accountService;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagFollowingRepository tagFollowingRepository;

    public List<TagFollowResDto> findAll() {
        Account account = accountService.getUserInfoData();
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(it -> fromEntityTagDto(it, account)).collect(Collectors.toList());
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public TagFollowResDto followTag(TagFollowReqDto tagFollowReqDto) throws AppException {
        Account account = accountService.getUserInfoData();
        Optional<Tag> tagOptional = tagRepository.findById(tagFollowReqDto.getTag().getId());
        if (!tagOptional.isPresent()) throw new AppException("TAG NOT FOUND!");
        Tag tag = tagOptional.get();
        Optional<TagFollowing> tagFollowingOptional = tagFollowingRepository.findFirstByTag_IdAndAccount_Id(tagFollowReqDto.getTag().getId(), account.getId());
        if (tagFollowingOptional.isPresent()) {
            TagFollowing tagFollowing = tagFollowingOptional.get();
            tagFollowingRepository.deleteById(tagFollowing.getId());
            tag.setFollow_count(tag.getFollow_count() - 1);
            return fromEntityTagDto(tag, account);
        }
        TagFollowing tagFollowing = new TagFollowing();
        tagFollowing.setAccount(account);
        tagFollowing.setTag(tagFollowReqDto.getTag());
        tag.setFollow_count(tag.getFollow_count() + 1);
        tagFollowingRepository.save(tagFollowing);
        return fromEntityTagDto(tag, account);
    }

    public TagFollowResDto fromEntityTagDto(Tag tag, Account currentUser) {
        TagFollowing tagFollowingOptional = null;
        if(currentUser != null){
            tagFollowingOptional = tagFollowingRepository.findFirstByTag_IdAndAccount_Id(tag.getId(), currentUser.getId()).orElse(null);
        }
        System.out.println(tagFollowingOptional);
        TagFollowResDto tagFollowResDto = new TagFollowResDto();
        tagFollowResDto.setId(tag.getId());
        tagFollowResDto.setName(tag.getName());
        tagFollowResDto.setTag_follow_count(tag.getFollow_count());
        tagFollowResDto.setFollow(tagFollowingOptional != null);
        return tagFollowResDto;
    }

    public Tag update(Tag tag, Long id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        Tag tagModal = optionalTag.get();
        tagModal.setName(tag.getName());
        return tagRepository.save(tagModal);
    }

    public void delete(Long id){
        tagRepository.deleteById(id);
    }
}
