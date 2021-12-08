package dev.galka.account.domain;

import dev.galka.account.adapters.out.AccountDbEntity;
import dev.galka.account.adapters.out.AccountRepository;

import java.util.List;

final class AccountFindPort {

    private final AccountRepository accountRepository;

    AccountFindPort(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    List<AccountDbEntity> findByUserId(Integer id) {
        return accountRepository.findByUserId(id);
    }
}
