package com.kiraz.messengerapp.dto;

import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ConversationDTO {
    private long id;
    private Date createdAt;
    private Date lastMessageAt;
    private String name;
    private Boolean isGroup;
    private List<Message> messages;
    private List<User> users;
}
