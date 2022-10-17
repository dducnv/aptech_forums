package com.example.forums_backend.entity;

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
@Table(name = "badges")
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private String description;
    private int status;
    @OneToMany(mappedBy = "badge", cascade = CascadeType.MERGE)
    private Set<UserBadge> userBadgeSet = new HashSet<>();
}
