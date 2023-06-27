package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.controller.UserController;
import com.kiraz.messengerapp.dto.MessageCreationRequest;
import com.kiraz.messengerapp.dto.MessageDTO;
import com.kiraz.messengerapp.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageConverter {

    public static Message convertMessageDTOToMessage(MessageDTO messageDTO){
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setBody(messageDTO.getBody());
        message.setImage(messageDTO.getImage());
        message.setCreatedAt(messageDTO.getCreatedAt());
        message.setSeenUsers(UserConverter.convertUserDTOListToUserList(messageDTO.getSeenUsers()));
        message.setConversation(ConversationConverter.convertConversationDTOToConversation(messageDTO.getConversation()));
        message.setSenderUser(UserConverter.convertUserDTOtoUser(messageDTO.getSenderUser()));
        return message;
    }

    public static MessageDTO convertMessageToMessageDTO(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setBody(message.getBody());
        messageDTO.setImage(message.getImage());
        messageDTO.setCreatedAt(message.getCreatedAt());
        messageDTO.setSeenUsers(UserConverter.convertUserListToUserDTOList(message.getSeenUsers()));
        messageDTO.setConversation(ConversationConverter.convertConversationToConversationDTO(message.getConversation()));
        messageDTO.setSenderUser(UserConverter.convertUsertoUserDTO(message.getSenderUser()));
        return messageDTO;
    }

    public static List<MessageDTO> convertMessageListToMessageDTOList(List<Message> messages){
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message: messages) {
            messageDTOList.add(convertMessageToMessageDTO(message));
        }

        return messageDTOList;
    }

    public static Message converMessageCreationRequestToMessage(MessageCreationRequest request) {
        Message message = new Message();
        message.setId(request.getId());
        message.setBody(request.getBody());
        message.setImage(request.getImage());
        return message;
    }
}
