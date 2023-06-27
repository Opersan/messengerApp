package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.controller.UserController;
import com.kiraz.messengerapp.dto.ConversationDTO;
import com.kiraz.messengerapp.dto.MessageCreationRequest;
import com.kiraz.messengerapp.dto.MessageDTO;
import com.kiraz.messengerapp.dto.UserDTO;
import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.Message;
import com.kiraz.messengerapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface MessageConverter {

    Message convertMessageDTOToMessage(MessageDTO messageDTO);

    MessageDTO convertMessageToMessageDTO(Message message);

    List<MessageDTO> convertMessageListToMessageDTOList(List<Message> messages);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seenUsers", ignore = true)
    @Mapping(target = "conversation", ignore = true)
    @Mapping(target = "senderUser", ignore = true)
    Message convertMessageCreationRequestToMessage(MessageCreationRequest request);

    ConversationDTO conversationToConversationDTO(Conversation conversation);

    Conversation conversationDTOToConversation(Conversation conversation);

    @Mapping(target="password", source = "hashedPassword")
    UserDTO convertUserToUserDTO(User user);

    @Mapping(target="hashedPassword", source = "password")
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "conversations", ignore = true)
    @Mapping(target = "seenMessages", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "messages", ignore = true)
    User convertUserDTOtoUser(UserDTO userDTO);

    Set<UserDTO> convertUserListToUserDTOList(Set<User> users);

    Set<User> convertUserDTOListToUserList(Set<UserDTO> userDTOList);
}
