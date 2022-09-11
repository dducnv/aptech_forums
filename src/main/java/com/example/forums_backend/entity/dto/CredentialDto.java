package com.example.forums_backend.entity.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CredentialDto {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
    private String tokenType;
    private String scope;
}
