package com.kiraz.messengerapp.converter;

import com.kiraz.messengerapp.dto.AccountDTO;
import com.kiraz.messengerapp.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "refresh_token", ignore = true)
    @Mapping(target = "session_state", ignore = true)
    @Mapping(target = "user", ignore = true)
    Account convertAccountDTOtoAccount(AccountDTO accountDTO);

    @Mapping(target = "token_type", ignore = true)
    AccountDTO convertAccountToAccountDTO(Account account);
}
