package dev.galka.account.inout;

import dev.galka.account.domain.User;

import java.math.BigDecimal;
import java.util.List;

public final class AccountFindPort {

    private final AccountRepository accountRepository;

    public AccountFindPort(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDbEntity> findByUserId(Integer id) {
        return accountRepository.findByUserId(id);
    }

    public BigDecimal getTotalBalance(User user) {
        return accountRepository.getTotalBalance(user);
    }

    public AccountDbEntity findById(Integer id) {
        return accountRepository.getById(id);
    }
}
