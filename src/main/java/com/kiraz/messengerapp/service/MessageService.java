package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.dto.MessageCreationRequest;
import com.kiraz.messengerapp.dto.MessageDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.MessageRepository;
import org.springframework.stereotype.Service;

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

    public Message createMessage(MessageCreationRequest request, Conversation conversation, User senderUser) {
        Message message = new Message();
        message.setBody(request.getBody());
        message.setImage(request.getImage());
        message.setConversation(conversation);
        message.setSenderUser(senderUser);
        message.addSeenUser(senderUser);
        return messageRepository.save(message);
    }

    public Message updateLastSeenMessage(Long messageId, User user) {
        Optional<Message> message = messageRepository.findById(messageId);
        message.get().addSeenUser(user);

        return messageRepository.save(message.get());
    }
}
