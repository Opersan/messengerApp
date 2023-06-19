package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.converter.ConversationConverter;
import com.kiraz.messengerapp.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/conversations")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ConversationController {
    private ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Conversation>> getAllUsers() {
        List<Conversation> conversations = conversationService.getAllConversations();
        return conversations.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    @GetMapping("/conversationById")
    public ConversationDTO getUserById(@RequestParam(value="id") Long id) {
        Optional<Conversation> conversation = conversationService.getConversation(id);
        if (conversation.isPresent()) {
            return ConversationConverter.convertConversationToConversationDTOConverter(conversation.get());
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
