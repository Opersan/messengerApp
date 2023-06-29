package com.kiraz.messengerapp.utils;

import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.Message;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ConversationUtils {
    //todo burayı tamamla
    public static Conversation orderConversationsByLastMessage(String direction, Conversation conversation){
        Comparator<Message> comparator = Comparator.comparing(Message::getCreatedAt);
        TreeSet<Message> set = conversation.getMessages().stream()
                .collect(Collectors.toCollection(() -> new TreeSet<>(comparator)));
        conversation.setMessages(set);

        if (direction.equalsIgnoreCase("asc")) {

        } else if (direction.equalsIgnoreCase("desc")) {

        }
        return conversation;
    }

    public static Set<Conversation> orderConversationsByLastMessage(String direction, Set<Conversation> conversationSet){
        Comparator<Message> comparator = Comparator.comparing(Message::getCreatedAt);
        for(Conversation conversation: conversationSet) {
            TreeSet<Message> set = conversation.getMessages().stream()
                    .collect(Collectors.toCollection(() -> new TreeSet<>(comparator)));
            conversation.setMessages(set);
        }
        if (direction.equalsIgnoreCase("asc")) {

        } else if (direction.equalsIgnoreCase("desc")) {

        }
        return conversationSet;
    }
}
