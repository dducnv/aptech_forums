package com.example.forums_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Comment comment;
    private String subject;
    private int type;
}
