package com.kiraz.messengerapp.service;

import com.kiraz.messengerapp.converter.AccountConverter;
import com.kiraz.messengerapp.converter.UserConverter;
import com.kiraz.messengerapp.dto.AccountDTO;
import com.kiraz.messengerapp.dto.ProfileDTO;
import com.kiraz.messengerapp.model.Account;
import com.kiraz.messengerapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccount(long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByProviderId(String providerId) {
        return accountRepository.findByProviderAccountId(providerId);
    }

    public Account saveAccountByAccountDTO(Account account) {
        return accountRepository.save(account);
    }

    public void updateAccountByAccountDTO(AccountDTO accountDTO) {
        Account account = accountRepository.findByProviderAccountId(accountDTO.getProviderAccountId()).get();

        account.setType(accountDTO.getType());
        account.setProvider(accountDTO.getProvider());
        account.setProviderAccountId(accountDTO.getProviderAccountId());
        account.setAccess_token(accountDTO.getAccess_token());
        account.setScope(accountDTO.getScope());
        account.setExpires_at(accountDTO.getExpires_at());
        account.setId_token(accountDTO.getId_token());

        accountRepository.save(account);
    }
}
