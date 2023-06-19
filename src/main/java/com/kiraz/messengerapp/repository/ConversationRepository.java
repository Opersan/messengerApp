package com.kiraz.messengerapp.repository;

import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
