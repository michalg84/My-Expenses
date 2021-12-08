package dev.galka.account.domain;

import dev.galka.account.adapters.out.AccountDbEntity;
import dev.galka.service.account.AccountDto;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;

import java.util.Date;

final class UserCreator {

    private final AuthUserProvider authUserProvider;
    private final AccountSavePort accountSavePort;
    private final MessageService messageService;


    UserCreator(AuthUserProvider authUserProvider, AccountSavePort accountSavePort, MessageService messageService) {
        this.authUserProvider = authUserProvider;
        this.accountSavePort = accountSavePort;
        this.messageService = messageService;
    }


    public void create(AccountDto dto) {
        dto.setCreationDate(new Date());
        AccountDbEntity account = AccountMapper.map(dto);
        account.setUser(authUserProvider.authenticatedUser());
        try {
            accountSavePort.save(account);
            messageService.addSuccessMessage("Account added !");
        } catch (Exception e) {
            messageService.addErrorMessage(String.format("Failed to add account %s", dto.getName()));
        }
    }
}
