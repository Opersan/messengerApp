package com.kiraz.messengerapp.repository;

import com.kiraz.messengerapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByProviderAccountId(String providerId);
}
