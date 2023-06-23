package com.kiraz.messengerapp.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
    @CreationTimestamp
    private Instant createdAt;

    @Column
    private Instant lastMessageAt;

    @Column
    private String name;

    @Column
    private Boolean isGroup;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Message> messages;

    // Child of User
    @ManyToMany(mappedBy = "conversations")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.PERSIST})
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getConversations().add(this);
    }

    public void addUserSet(Set<User> users) {
        for (User user: users) {
            this.users.add(user);
            user.getConversations().add(this);
        }
    }

    public void deleteMessages() {
        for (Message message: messages) {
            message.setConversation(null);
        }
        messages.removeAll(this.messages);
    }

    public void deleteUsers() {
        for (User user: users) {
            user.getConversations().remove(this);
        }
        users.removeAll(users);
    }
}
