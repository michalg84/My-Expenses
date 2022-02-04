package dev.galka.account.domain;

import dev.galka.account.inout.AccountDbEntity;
import dev.galka.account.inout.AccountFindPort;
import dev.galka.dto.TransactionDto;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;

public class AccountUpdater {

    private final AuthUserProvider authUserProvider;
    private final AccountSavePort accountSavePort;
    private final AccountFindPort accountFindPort;
    private final MessageService messageService;
    private AccountMapper mapper = new AccountMapper();


    AccountUpdater(AuthUserProvider authUserProvider,
                   AccountSavePort accountSavePort,
                   AccountFindPort accountFindPort,
                   MessageService messageService) {
        this.authUserProvider = authUserProvider;
        this.accountFindPort = accountFindPort;
        this.messageService = messageService;
        this.accountSavePort = accountSavePort;
    }

    public void updateAccountBalance(TransactionDto transactionDto) {
        AccountDbEntity account = accountFindPort.findById(transactionDto.getAccount().getId());
        account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        accountSavePort.save(account);
    }
}
