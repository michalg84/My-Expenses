package dev.galka.account.domain;

import dev.galka.account.inout.AccountFindPort;
import dev.galka.account.inout.AccountRepository;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AccountConfig {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AuthUserProvider authUserProvider;


    @Bean
    AccountSavePort accountSavePort() {
        return new AccountSavePort(accountRepository);
    }

    @Bean
    AccountFindPort accountFindPort() {
        return new AccountFindPort(accountRepository);
    }


    @Bean
    AccountApi accountApi() {
        final AccountCreator creator = new AccountCreator(authUserProvider, accountSavePort(), messageService);
        final AccountProvider provider = new AccountProvider(authUserProvider, accountFindPort());
        final AccountUpdater updater = new AccountUpdater(authUserProvider,
                accountSavePort(),
                accountFindPort(),
                messageService);
        return new AccountApi(creator, provider, updater);
    }
}
