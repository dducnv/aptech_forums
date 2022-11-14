package com.example.forums_backend.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchDto {
    private String title;
    private String slug;
    private int comment_count;
    private int vote_count;
    private int bookmark_count;
}
