package com.kiraz.messengerapp.repository;

import com.kiraz.messengerapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
