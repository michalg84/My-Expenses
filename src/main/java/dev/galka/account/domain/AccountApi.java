package dev.galka.account.domain;

import dev.galka.account.dto.AccountDto;
import dev.galka.account.dto.AccountIdNameDtoView;
import dev.galka.dto.TransactionDto;

import java.util.List;
import java.util.stream.Collectors;

public final class AccountApi {

    private final AccountCreator creator;
    private final AccountProvider provider;


    public AccountApi(AccountCreator creator, AccountProvider provider) {
        this.creator = creator;
        this.provider = provider;
    }

    public void createAccount(AccountDto dto) {
        creator.create(dto);
    }

    public TransactionDto buildTransactionView() {
        final List<AccountDto> accounts = provider.findAll();
        final List<AccountIdNameDtoView> accountIdNameDtoViews = accounts.stream().map(AccountDto::createIdNameDto).collect(Collectors.toList());
        return TransactionDto.builder()
                .accountsIdAndNameList(accountIdNameDtoViews)
                .build();
    }

    public List<AccountDto> findAllAccounts() {
        return provider.findAll();
    }
}
