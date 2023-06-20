package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Optional<Conversation> getConversationByUser(User user1, User user2){
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        List<Conversation> conversation = conversationRepository.findAll();
        return conversation.stream().filter(e -> e.getUsers().containsAll(users)).findAny();
    }

    public Set<Conversation> getAllConversationByUser(User user){
        return user.getConversations();
    }

    public Conversation createConversation(User user1, User user2) {
        Conversation conversation = new Conversation();

        conversation.addUser(user1);
        conversation.addUser(user2);

        return conversationRepository.save(conversation);
    }

    public Conversation createGroupConversation(Set<User> userList) {
        Conversation conversation = new Conversation();

        conversation.setUsers(userList);

        return conversationRepository.save(conversation);
    }

}
