package com.example.forums_backend.entity;

import com.example.forums_backend.entity.my_enum.ContactType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_contact")
public class UserContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String icon;
    private String name;
    private ContactType type;
    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;
}
