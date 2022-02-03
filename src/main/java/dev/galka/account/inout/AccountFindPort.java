package dev.galka.account.inout;

import java.util.List;

public final class AccountFindPort {

    private final AccountRepository accountRepository;

    public AccountFindPort(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDbEntity> findByUserId(Integer id) {
        return accountRepository.findByUserId(id);
    }
}
