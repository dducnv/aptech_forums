package com.example.forums_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private Account account;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    private String content;
    @OneToMany
    private Set<Comment> reply_to = new HashSet<Comment>();
    private int vote_count = 0;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    Set<Voting> voting = new HashSet<>();
    @OneToMany(mappedBy = "comment",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    Set<Bookmark> bookmarks = new HashSet<>();
}
