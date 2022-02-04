package dev.galka.service;

import dev.galka.account.domain.AccountApi;
import dev.galka.service.user.AuthUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionsConfig {

    @Autowired
    private AuthUserProvider authUserProvider;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountApi accountApi;

    @Bean
    TransactionApi transactionApi() {
        return new TransactionApi(authUserProvider, accountApi, transactionService);
    }

}
