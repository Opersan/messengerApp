package com.kiraz.messengerapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Setter
@Getter
public class MessageDTO {

    private Long id;
    private String body;
    private String image;
    private Instant createdAt;
    private Set<UserDTO> seenUsers;
    private ConversationDTO conversation;
    private UserDTO senderUser;
}
