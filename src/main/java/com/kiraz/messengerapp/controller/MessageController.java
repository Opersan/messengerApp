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
    public List<MessageDTO> getAllUsers() {
        List<Message> messages = messageService.getAllMessages();
        return messages.isEmpty() ? null : MessageConverter.convertMessageListToMessageDTOList(messages);
    }

    @PostMapping("/createMessage")
    public MessageDTO createMessage(@RequestBody MessageCreationRequest request) {
        User senderUser = userController.getUserById(request.getSenderUserId());
        Conversation conversation = ConversationConverter.convertConversationDTOToConversation(
                conversationController.getConversationById(Long.valueOf(request.getConversationId())));
        Message messages = messageService.createMessage(request, conversation, senderUser);
        if (messages == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return MessageConverter.convertMessageToMessageDTO(messages);
    }

    @PutMapping("/updateMessage")
    public MessageDTO updateLastSeenMessage(@JsonArg("messageId") Long messageId, @JsonArg("userId") Long userId) {
        User seenUser = userController.getUserById(userId);
        MessageDTO updatedMessage = MessageConverter.convertMessageToMessageDTO(messageService.updateLastSeenMessage(messageId, seenUser));
        return updatedMessage;
    }

    @GetMapping("/messagesByConversationId")
    public List<MessageDTO> getAllUsersExceptItself(@RequestParam(value="conversationId") Long id) {
        List<MessageDTO> messages = MessageConverter.convertMessageListToMessageDTOList(messageService.getAllMessagesByConversationId(id));
        if (messages.isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return messages;
    }
}