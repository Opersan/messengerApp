package com.kiraz.messengerapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.sql.Date;
import java.util.List;

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
    private Date createdAt;

    @ManyToMany(mappedBy = "seenMessages")
    private List<User> seenUsers;

    @ManyToOne()
    @JoinColumn(name = "conversation_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Conversation conversation;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private User senderUser;
}
