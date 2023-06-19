package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class ConversationConverter {
    public static List<ConversationDTO> convertConversationToConversationDTOList(List<Conversation> conversations){
        List<ConversationDTO> conversationDTOList = new ArrayList<>();
        for (Conversation conversation: conversations) {
            conversationDTOList.add(convertConversationToConversationDTOConverter(conversation));
        }

        return conversationDTOList;
    }
    public static Conversation convertConversationDTOToConversationConverter(ConversationDTO conversationDTO){
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

    public static ConversationDTO convertConversationToConversationDTOConverter(Conversation conversation){
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
}
