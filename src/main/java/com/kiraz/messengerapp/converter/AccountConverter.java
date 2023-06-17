package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.AccountDTO;
import com.kiraz.messengerapp.model.Account;

public class AccountConverter {

    public static Account convertAccountDTOtoAccount(AccountDTO accountDTO) {
        Account account = new Account();

        account.setType(accountDTO.getType());
        account.setProvider(accountDTO.getProvider());
        account.setProviderAccountId(accountDTO.getProviderAccountId());
        account.setAccess_token(accountDTO.getAccess_token());
        account.setScope(accountDTO.getScope());
        account.setExpires_at(accountDTO.getExpires_at());
        account.setId_token(accountDTO.getId_token());

        return account;
    }

    public static AccountDTO convertAccountToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setType(account.getType());
        accountDTO.setProvider(account.getProvider());
        accountDTO.setProviderAccountId(accountDTO.getProviderAccountId());
        accountDTO.setAccess_token(accountDTO.getAccess_token());
        accountDTO.setScope(accountDTO.getScope());
        accountDTO.setExpires_at(accountDTO.getExpires_at());
        accountDTO.setId_token(accountDTO.getId_token());

        return accountDTO;
    }

}
