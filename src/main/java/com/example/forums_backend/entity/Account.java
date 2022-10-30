package com.example.forums_backend.entity;

import com.example.forums_backend.entity.my_enum.AuthProvider;
import com.example.forums_backend.entity.my_enum.StatusEnum;
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
    private String imageUrl; //null hoặc sử dụng link ảnh
    private String name;//not null
    @JsonIgnore
    @Column(unique = true)
    private String email;// not null - unique
    @Column(unique = true)
    private String username;//not null - unique
    @JsonIgnore
    private String password; //null
    private boolean email_verify;//true - false
    private boolean fpt_member; // true - false
    @Column(columnDefinition = "int(11) default 0")
    private int reputation; // default 0
    @Enumerated(EnumType.STRING)
    private AuthProvider provider; // enum
    private String providerId;// null khi là local
    @JsonIgnore
    private String one_time_password; // null
    @JsonIgnore
    private Date expire_time; //null
    @Column(columnDefinition = "varchar(255) default 'USER'")
    private String role; //USER - ADMIN
    @CreationTimestamp
    private LocalDateTime createdAt; //null
    @UpdateTimestamp
    private LocalDateTime updatedAt; //null
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    Set<Post> posts = new HashSet<>(); //null
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    Set<TagFollowing> tagFollowings = new HashSet<>(); // null
    @OneToMany(mappedBy = "account", cascade = CascadeType.MERGE)
    Set<UserContact> userContacts = new HashSet<>(); //null
    private StatusEnum status;
}
