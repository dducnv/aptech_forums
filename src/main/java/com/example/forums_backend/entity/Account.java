package com.example.forums_backend.entity;

import com.example.forums_backend.entity.my_enum.AuthProvider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT" )
    //avatar nhưng là link
    private String imageUrl;
    private String name;
    private String email;
    private String password;
    private boolean email_verify;
    private boolean fpt_member;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    private String one_time_password;
    private Date expire_time;
    @Column(columnDefinition = "varchar(255) default 'USER'")
    private String role;
    @CreationTimestamp
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    protected LocalDateTime updatedAt;
}
