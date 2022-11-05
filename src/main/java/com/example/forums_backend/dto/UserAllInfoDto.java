package com.example.forums_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class UserAllInfoDto {
    private String avatar; //null hoặc sử dụng link ảnh
    private String name;//not null
    private String email;
    private String username;//not null - unique
    private String skill;
    private String bio;
    private int reputation; // default 0
    private String role; //USER - ADMIN
    private LocalDateTime createdAt; //null
    private int post_count;
    private int comment_count;
    private int tag_flowing_count;
    private int badge_count;
}
