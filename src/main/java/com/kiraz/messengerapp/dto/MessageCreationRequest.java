package com.kiraz.messengerapp.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageCreationRequest {
    private Long id;
    private String body;
    private String image;
    private Long seenUserId;
    private String conversationId;
    private Long senderUserId;
}
