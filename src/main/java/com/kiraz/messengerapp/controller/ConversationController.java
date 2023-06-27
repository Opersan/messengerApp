package com.kiraz.messengerapp.controller;

import com.kiraz.messengerapp.annotation.JsonArg;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.dto.GroupConversationDTO;
import com.kiraz.messengerapp.dto.GroupUserDTO;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.converter.ConversationConverter;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.service.ConversationService;
import jakarta.persistence.JoinColumn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/conversations")
@CrossOrigin(origins = {"http://localhost:3000"})
public class ConversationController {
    private ConversationService conversationService;

    private UserController userController;

    private ConversationConverter conversationConverter;

    public ConversationController(ConversationService conversationService, UserController userController,
                                  ConversationConverter conversationConverter) {
        this.conversationService = conversationService;
        this.userController = userController;
        this.conversationConverter = conversationConverter;
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Conversation>> getAllConversations() {
        List<Conversation> conversations = conversationService.getAllConversations();
        return conversations.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    @GetMapping("/conversationById")
    public ResponseEntity<ConversationDTO> getConversation(@RequestParam(value = "conversationId") Long id) {
        Optional<Conversation> conversation = conversationService.getConversation(id);
        if (conversation.isPresent()) {
            return new ResponseEntity<>(conversationConverter.convertConversationToConversationDTO(conversation.get()), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    public ConversationDTO getConversationById(Long id) {
        Optional<Conversation> conversation = conversationService.getConversation(id);
        if (conversation.isPresent()) {
            return conversationConverter.convertConversationToConversationDTO(conversation.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/conversationByUserId")
    public ResponseEntity<ConversationDTO> getConversationByUserId(@RequestParam(value = "senderId") Long id1, @RequestParam(value = "receiverId") Long id2) {
        User user1 = userController.getUserById(id1);
        User user2 = userController.getUserById(id2);
        Optional<Conversation> conversation = conversationService.getConversationByUser(user1, user2);
        return conversation.isPresent() ?
                new ResponseEntity<>(conversationConverter.convertConversationToConversationDTO(conversation.get()), HttpStatus.OK) : null;
    }

    @GetMapping("/allConversationsByUserId")
    public ResponseEntity<Set<ConversationDTO>> getAllConversationByUserId(@RequestParam Long id) {
        User user = userController.getUserById(id);
        Set<Conversation> conversations = conversationService.getAllConversationByUser(user);
        if (!conversations.isEmpty()) {
            return new ResponseEntity<>(conversationConverter.convertConversationSetToConversationDTOSet(conversations), HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/createConversation")
    public ResponseEntity<ConversationDTO> createConversation(@JsonArg("senderUser") UserDTO user1, @JsonArg("receiverUser") UserDTO user2) {
        Conversation createdConversation = conversationService.createConversation(userController.getUserById(user1.getId()),
                userController.getUserById(user2.getId()));

        return new ResponseEntity<>(conversationConverter.convertConversationToConversationDTO(createdConversation), HttpStatus.OK);
    }

    @PostMapping("/createGroupConversation")
    public ResponseEntity<ConversationDTO> createGroupConversation(@RequestBody GroupConversationDTO groupConversationDTOS) {
        Set<User> users = new HashSet<>();

        for (GroupUserDTO groupUserDTO: groupConversationDTOS.getMembers()) {
            users.add(userController.getUserById(groupUserDTO.getValue()));
        }

        Conversation createdConversation = conversationService.createGroupConversation(groupConversationDTOS.getName(), users);

        return new ResponseEntity<>(conversationConverter.convertConversationToConversationDTO(createdConversation), HttpStatus.OK);
    }

    @PutMapping("/updateConversation")
    public ResponseEntity<ConversationDTO> updateConversationLastMessageAt(@JsonArg("conversationId") String conversationId) {
        ConversationDTO conversation = conversationConverter.convertConversationToConversationDTO(conversationService.updateConversationLastMessageAt(Long.valueOf(conversationId)));
        if (conversation == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(conversation, HttpStatus.OK);
    }

    @DeleteMapping("/deleteConversation")
    public ResponseEntity<ConversationDTO> deleteConversation(@RequestParam("conversationId") Long conversationId, @RequestParam("userId") Long userId){
        User user = userController.getUserById(userId);
        ConversationDTO conversationDTO = conversationConverter.convertConversationToConversationDTO
                (conversationService.deleteConversation(conversationId, user).get());

        return new ResponseEntity<>(conversationDTO, HttpStatus.OK);
    }
}
