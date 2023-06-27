package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.model.Conversation;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ConversationConverter {

    List<ConversationDTO> convertConversationToConversationDTOList(List<Conversation> conversations);

    Conversation convertConversationDTOToConversation(ConversationDTO conversationDTO);

    ConversationDTO convertConversationToConversationDTO(Conversation conversation);

    Set<ConversationDTO> convertConversationSetToConversationDTOSet(Set<Conversation> conversations);
}
