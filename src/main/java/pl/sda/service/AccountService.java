package pl.sda.service;

import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;
import pl.sda.model.Account;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
public interface AccountService {
    List<AccountDto> getUserAccounts();

    void addAccount(AccountDto accountDto);

    void updateAccountBalance(TransactionDto transactionDto);


}
