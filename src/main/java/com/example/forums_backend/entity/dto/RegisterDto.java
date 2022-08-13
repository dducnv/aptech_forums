package com.example.forums_backend.entity.dto;

import lombok.*;
    import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotNull(message = "name shouldn't be null")
    @NotBlank
    private String name;
    @Email(message = "invalid email address")
    private String email;
    @NotNull(message = "phone shouldn't be null")
    @NotBlank
    private String phone;
    @NotNull(message = "password shouldn't be null")
    @NotBlank
    private String password;

    private String confirmPassword;
}
