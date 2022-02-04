package dev.galka.service;

import dev.galka.account.domain.AccountApi;
import dev.galka.account.dto.AccountIdNameDtoView;
import dev.galka.dto.TransactionDto;
import dev.galka.service.user.AuthUserProvider;

import java.util.List;

public final class TransactionApi {

    private final AuthUserProvider authUserProvider;
    private final TransactionService transactionService;
    private final AccountApi accountApi;

    public TransactionApi(AuthUserProvider authUserProvider,
                          AccountApi accountApi,
                          TransactionService transactionService) {
        this.transactionService = transactionService;
        this.accountApi = accountApi;
        this.authUserProvider = authUserProvider;
    }

    public TransactionDto getTransactionView() {
        final List<AccountIdNameDtoView> accountIdNameDtoViews = accountApi.buildAccountIdNameDtoView();
        return TransactionDto.builder()
                .accountsIdAndNameList(accountIdNameDtoViews)
                .build();
    }

    public List<TransactionDto> getTransactions() {
        return transactionService.getTransactionsWithBalance();
    }
}
