package dev.galka.account.domain;

import dev.galka.account.dto.AccountDto;
import dev.galka.account.inout.AccountDbEntity;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;

final class AccountCreator {

    private final AuthUserProvider authUserProvider;
    private final AccountSavePort accountSavePort;
    private final MessageService messageService;
    private AccountMapper mapper = AccountMapper.INSTANCE;


    AccountCreator(AuthUserProvider authUserProvider, AccountSavePort accountSavePort, MessageService messageService) {
        this.authUserProvider = authUserProvider;
        this.accountSavePort = accountSavePort;
        this.messageService = messageService;
    }


    public void create(AccountDto dto) {

        AccountDbEntity account = mapper.map(dto);
        account.setUser(authUserProvider.authenticatedUser());
        try {
            accountSavePort.save(account);
            messageService.addSuccessMessage("Account added !");
        } catch (Exception e) {
            messageService.addErrorMessage(String.format("Failed to add account %s", dto.getName()));
        }
    }
}
