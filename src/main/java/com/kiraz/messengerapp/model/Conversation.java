package com.kiraz.messengerapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "conversations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Date createdAt;

    @Column
    private Date lastMessageAt;

    @Column
    private String name;

    @Column
    private Boolean isGroup;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    @ManyToMany(mappedBy = "conversations")
    private List<User> users;
}
