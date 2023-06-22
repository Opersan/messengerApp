package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.dto.GroupConversationDTO;
import com.kiraz.messengerapp.model.Conversation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConversationConverter {
    public static List<ConversationDTO> convertConversationToConversationDTOList(List<Conversation> conversations){
        List<ConversationDTO> conversationDTOList = new ArrayList<>();
        for (Conversation conversation: conversations) {
            conversationDTOList.add(convertConversationToConversationDTO(conversation));
        }

        return conversationDTOList;
    }
    public static Conversation convertConversationDTOToConversation(ConversationDTO conversationDTO){
        Conversation conversation = new Conversation();
        conversation.setId(conversationDTO.getId());
        conversation.setCreatedAt(conversationDTO.getCreatedAt());
        conversation.setLastMessageAt(conversation.getLastMessageAt());
        conversation.setName(conversationDTO.getName());
        conversation.setIsGroup(conversation.getIsGroup());
        conversation.setMessages(conversationDTO.getMessages());
        conversation.setUsers(conversationDTO.getUsers());
        return conversation;
    }

    public static ConversationDTO convertConversationToConversationDTO(Conversation conversation){
        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setId(conversation.getId());
        conversationDTO.setCreatedAt(conversation.getCreatedAt());
        conversationDTO.setLastMessageAt(conversation.getLastMessageAt());
        conversationDTO.setName(conversation.getName());
        conversationDTO.setIsGroup(conversation.getIsGroup());
        conversationDTO.setMessages(conversation.getMessages());
        conversationDTO.setUsers(conversation.getUsers());
        return conversationDTO;
    }

    public static Set<ConversationDTO> convertConversationSetToConversationDTOSet(Set<Conversation> conversations){
        Set<ConversationDTO> conversationDTOS = new HashSet<>();
        for (Conversation conversation: conversations) {
            ConversationDTO conversationDTO = new ConversationDTO();
            conversationDTO.setId(conversation.getId());
            conversationDTO.setCreatedAt(conversation.getCreatedAt());
            conversationDTO.setLastMessageAt(conversation.getLastMessageAt());
            conversationDTO.setName(conversation.getName());
            conversationDTO.setIsGroup(conversation.getIsGroup());
            conversationDTO.setMessages(conversation.getMessages());
            conversationDTO.setUsers(conversation.getUsers());
            conversationDTOS.add(conversationDTO);
        }

        return conversationDTOS;
    }
}
