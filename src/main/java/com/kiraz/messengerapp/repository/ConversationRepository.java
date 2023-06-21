package com.kiraz.messengerapp.repository;

import com.kiraz.messengerapp.model.Conversation;
import com.kiraz.messengerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    Optional<Conversation> findByUsersIn(Set<User> users);

    List<Conversation> findAllByUsers(User user);
}
