package com.example.forums_backend.dto;

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
}
