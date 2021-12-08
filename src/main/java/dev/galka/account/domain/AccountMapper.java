package dev.galka.account.domain;

import dev.galka.account.adapters.out.AccountDbEntity;
import dev.galka.service.account.AccountDto;

public final class AccountMapper {

    private AccountMapper() {
    }

    public static AccountDbEntity map(AccountDto newAccount) {
        AccountDbEntity account = new AccountDbEntity();
        account.setAccountNumber(newAccount.getAccountNumber());
        account.setAccountType(newAccount.getAccountType());
        account.setBalance(newAccount.getBalance());
        account.setCreationDate(newAccount.getCreationDate());
        account.setName(newAccount.getName());
        return account;
    }

    public static AccountDto map(AccountDbEntity account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setName(account.getName());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        accountDto.setCreationDate(account.getCreationDate());
        return accountDto;
    }
}