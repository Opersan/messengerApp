package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.converter.AccountConverter;
import com.kiraz.messengerapp.dto.AccountDTO;
import com.kiraz.messengerapp.model.Account;
import com.kiraz.messengerapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO saveAccount(AccountDTO accountDTO) {
        Account account = AccountConverter.convertAccountDTOtoAccount(accountDTO);
        return AccountConverter.convertAccountToAccountDTO(accountRepository.save(account));
    }
}
