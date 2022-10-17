package com.example.forums_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(unique = true)
    private String slug;
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "int(11) default 0")
    private int vote_count;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false)
    private Account author;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.MERGE, targetEntity = Tag.class)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties("posts")
    Set<Tag> tags = new HashSet<>();
    @OneToMany(mappedBy = "post",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Voting> voting = new HashSet<>();
    @OneToMany(mappedBy = "post",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Comment> comment = new HashSet<>();
    @OneToMany(mappedBy = "post",cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Bookmark> bookmarks = new HashSet<>();
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
