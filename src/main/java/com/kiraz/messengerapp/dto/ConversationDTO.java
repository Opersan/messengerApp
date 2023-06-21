package com.kiraz.messengerapp.dto;

import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ConversationDTO {
    private long id;
    private Instant createdAt;
    private Instant lastMessageAt;
    private String name;
    private Boolean isGroup;
    private Set<Message> messages;
    private Set<User> users;
}
