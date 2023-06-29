package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.dto.MessageCreationRequest;
import com.kiraz.messengerapp.dto.MessageDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByConversationId(Long id) {
        return messageRepository.findAllByConversation_IdOrderByCreatedAtAsc(id);
    }

    public Message createMessage(Message message, Conversation conversation, User senderUser, User seenUser) {
        message.setConversation(conversation);
        message.setSenderUser(senderUser);
        message.addSeenUser(seenUser);
        return messageRepository.save(message);
    }

    public Message updateLastSeenMessage(Long messageId, User user) {
        Optional<Message> message = messageRepository.findById(messageId);
        message.get().addSeenUser(user);

        return messageRepository.save(message.get());
    }
}
