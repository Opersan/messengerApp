package com.kiraz.messengerapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Date;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private Instant emailVerified;

    @Column
    private String image;

    @Column
    private String hashedPassword;

    @Column
    @CreationTimestamp
    private Instant createdAt;

    @Column
    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToMany
    @JoinTable(name = "conversation_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Conversation> conversations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "seen_messages",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"))
    @JsonManagedReference
    private Set<Message> seenMessages = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account account;

    // @JsonManagedReference ve @JsonBackReference anotasyonları
    // Jackson'ın infinite recursion sorunun çözmek için yazıldı
    // Managed ile owning side ve back ile other side belirtildi
    // çoklu ilişkilerde referansa yardımcı olmak zorunlu @JsonIgnore yerine kullanmak lazım
    // yoksa rest data eksik gidiyor.
    @OneToMany(mappedBy = "senderUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Message> messages;

    public void addConversation(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.getUsers().add(this);
    }

    public void addSeenMessage(Message message) {
        this.messages.add(message);
        message.getSeenUsers().add(this);
    }
}
