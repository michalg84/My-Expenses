package dev.galka.service.account;

import dev.galka.dto.TransactionDto;
import dev.galka.model.AccountType;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void addAccount(AccountDto accountDto);

    void updateAccountBalance(TransactionDto transactionDto);

    BigDecimal getTotalBalance();

    List<AccountDto> getAccounts();

    List<AccountType> getAccountTypes();
}
