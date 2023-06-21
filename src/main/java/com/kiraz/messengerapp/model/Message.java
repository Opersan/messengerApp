package com.kiraz.messengerapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String body;

    @Column
    private String image;

    @Column
    @CreationTimestamp
    private Instant createdAt;

    @ManyToMany(mappedBy = "seenMessages")
    @JsonBackReference
    private Set<User> seenUsers = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "conversation_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonBackReference
    private Conversation conversation;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonBackReference
    private User senderUser;

    public void addSeenUser(User user) {
        this.seenUsers.add(user);
        user.getSeenMessages().add(this);
    }
}
