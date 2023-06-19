package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    private ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository){
        this.conversationRepository = conversationRepository;
    }

    public List<Conversation> getAllConversations(){
        return conversationRepository.findAll();
    }

    public Optional<Conversation> getConversation(long id){
        return conversationRepository.findById(id);
    }

}
