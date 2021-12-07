package dev.galka.account.adapters;

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
    AuthUserProvider getAuthUserProvider() {
        return new AuthUserProviderImpl(userRepository);
    }

    @Bean
    AccountApi getAccountApi() {
        return new AccountApi(new UserCreator(getAuthUserProvider(), accountRepository, messageService));
    }
}
