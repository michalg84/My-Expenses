package dev.galka.account.domain;

import dev.galka.account.dto.AccountDto;
import dev.galka.account.dto.AccountIdNameDtoView;
import dev.galka.dto.TransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public final class AccountApi {

    private final AccountCreator creator;
    private final AccountProvider provider;
    private final AccountUpdater updater;


    public AccountApi(AccountCreator creator, AccountProvider provider, AccountUpdater updater) {
        this.creator = creator;
        this.provider = provider;
        this.updater = updater;
    }

    public void createAccount(AccountDto dto) {
        creator.create(dto);
    }

    public List<AccountIdNameDtoView> buildAccountIdNameDtoView() {
        final List<AccountDto> accounts = provider.findAll();
        return accounts.stream()
                .map(AccountDto::createIdNameDto)
                .collect(Collectors.toList());

    }

    public List<AccountDto> findAllAccounts() {
        return provider.findAll();
    }

    public BigDecimal getTotalBalance() {
        return provider.getTotalBalance();
    }

    public void updateAccountBalance(TransactionDto transactionDto) {
        updater.updateAccountBalance(transactionDto);
    }
}
