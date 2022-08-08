package com.example.forums_backend.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CredentialDto {
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
    private String scope;
}
