package dev.galka.account.domain;

import dev.galka.account.adapters.out.AccountRepository;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.user.AuthUserProviderImpl;
import dev.galka.service.user.UserRepository;
import dev.galka.service.webnotification.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageService messageService;

    @Bean
    AccountSavePort accountSavePort() {
        return new AccountSavePort(accountRepository);

    }

    @Bean
    AccountFindPort accountfindPort() {
        return new AccountFindPort(accountRepository);

    }


    @Bean
    AuthUserProvider authUserProvider() {
        return new AuthUserProviderImpl(userRepository);
    }

    @Bean
    AccountApi accountApi() {
        return new AccountApi(new UserCreator(authUserProvider(), accountSavePort(), messageService));
    }
}
