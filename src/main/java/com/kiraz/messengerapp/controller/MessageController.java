package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.annotation.JsonArg;
import com.kiraz.messengerapp.converter.ConversationConverter;
import com.kiraz.messengerapp.converter.MessageConverter;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.dto.MessageCreationRequest;
import com.kiraz.messengerapp.dto.MessageDTO;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private ConversationController conversationController;
    private UserController userController;
    private MessageService messageService;

    public MessageController(ConversationController conversationController,
                             UserController userController, MessageService messageService) {
        this.conversationController = conversationController;
        this.userController = userController;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDTO>> getAllUsers() {
        List<Message> messages = messageService.getAllMessages();
        return messages.isEmpty() ? null : new ResponseEntity<>(MessageConverter.convertMessageListToMessageDTOList(messages), HttpStatus.OK);
    }

    @PostMapping("/createMessage")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageCreationRequest request) {
        User senderUser = userController.getUserById(request.getSenderUserId());
        User seenUser = userController.getUserById(request.getSeenUserId());
        Conversation conversation = ConversationConverter.convertConversationDTOToConversation(
                conversationController.getConversationById(Long.valueOf(request.getConversationId())));
        Message messages = messageService.createMessage(MessageConverter.converMessageCreationRequestToMessage(request), conversation, senderUser, seenUser);
        if (messages == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(MessageConverter.convertMessageToMessageDTO(messages), HttpStatus.OK);
    }

    @PutMapping("/updateMessage")
    public ResponseEntity<MessageDTO> updateLastSeenMessage(@JsonArg("messageId") Long messageId, @JsonArg("userId") Long userId) {
        User seenUser = userController.getUserById(userId);
        MessageDTO updatedMessage = MessageConverter.convertMessageToMessageDTO(messageService.updateLastSeenMessage(messageId, seenUser));
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @GetMapping("/messagesByConversationId")
    public ResponseEntity<List<MessageDTO>> getAllUsersExceptItself(@RequestParam(value="conversationId") Long id) {
        List<MessageDTO> messages = MessageConverter.convertMessageListToMessageDTOList(messageService.getAllMessagesByConversationId(id));
        if (messages.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
