package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import com.kiraz.messengerapp.repository.ConversationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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
        return new HashSet<>(conversationRepository.findAllByUsers(user));
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

    public Conversation updateConversationLastMessageAt(Long conversationId) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        conversation.get().setLastMessageAt(Instant.now());
        return conversationRepository.save(conversation.get());
    }

    public Conversation deleteConversation(Long id, User user) {
        Optional<Conversation> conversation = conversationRepository.findById(id);
        if (conversation.get().getUsers().contains(user)) {
            for (Message message: conversation.get().getMessages()) {
                    message.removeSeenUsers(message.getSeenUsers());
            }
            conversation.get().deleteMessages();
            conversation.get().deleteUsers();
            conversationRepository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return conversation.get();
    }

}
