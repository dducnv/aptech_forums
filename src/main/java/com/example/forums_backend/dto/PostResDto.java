package com.example.forums_backend.dto;

import com.example.forums_backend.entity.Account;
import com.example.forums_backend.entity.Tag;
import com.example.forums_backend.entity.Voting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResDto {
    private String title;
    private String content;
    private List<Tag> tags;
    private Account account;
    private boolean isVote;
    private int voteType;
    private int voteCount;
}
