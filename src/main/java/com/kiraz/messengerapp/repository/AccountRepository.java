package com.kiraz.messengerapp.repository;

import com.kiraz.messengerapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
