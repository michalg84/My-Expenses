package dev.galka.account.adapters;

import dev.galka.account.adapters.out.AccountRepository;
import dev.galka.account.domain.model.Account;
import dev.galka.service.account.AccountDto;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;

import java.util.Date;

final class UserCreator {

    private final AuthUserProvider authUserProvider;
    private final AccountRepository repository;
    private final MessageService messageService;


    UserCreator(AuthUserProvider authUserProvider, AccountRepository repository, MessageService messageService) {
        this.authUserProvider = authUserProvider;
        this.repository = repository;
        this.messageService = messageService;
    }


    public void create(AccountDto dto) {
        dto.setCreationDate(new Date());
        Account account = AccountMapper.map(dto);
        account.setUser(authUserProvider.authenticatedUser());
        try {
            repository.save(account);
            messageService.addSuccessMessage("Account added !");
        } catch (Exception e) {
            messageService.addErrorMessage(String.format("Failed to add account %s", dto.getName()));
        }
    }
}
