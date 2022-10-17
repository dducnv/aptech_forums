package com.example.forums_backend.entity;

import com.example.forums_backend.entity.my_enum.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String imageUrl;
    private String name;
    @JsonIgnore
    @Column(unique = true)
    private String email;
    @JsonIgnore
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private boolean email_verify;
    private boolean fpt_member;
    private int reputation;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;
    @JsonIgnore
    private String one_time_password;
    @JsonIgnore
    private Date expire_time;
    @Column(columnDefinition = "varchar(255) default 'USER'")
    private String role;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    Set<Post> posts = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    Set<TagFollowing> tagFollowings = new HashSet<>();
    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    Set<UserContact> userContacts = new HashSet<>();
}
