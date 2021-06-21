package pl.sda.service;

import java.math.BigDecimal;
import java.util.List;
import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
public interface AccountService {
    List<AccountDto> getUserAccounts();

    void addAccount(AccountDto accountDto);

    void updateAccountBalance(TransactionDto transactionDto);

    BigDecimal getTotalBalance();

    List<AccountDto> getAccounts();
}
