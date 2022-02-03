package dev.galka.account.domain;

import dev.galka.account.inout.AccountDbEntity;
import dev.galka.account.inout.AccountRepository;

final class AccountSavePort {

    private final AccountRepository accountRepository;

    AccountSavePort(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    void save(AccountDbEntity account) {
        accountRepository.save(account);
    }
}
