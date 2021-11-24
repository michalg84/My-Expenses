package pl.sda.service.account;

import pl.sda.dto.TransactionDto;
import pl.sda.model.AccountType;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void addAccount(AccountDto accountDto);

    void updateAccountBalance(TransactionDto transactionDto);

    BigDecimal getTotalBalance();

    List<AccountDto> getAccounts(Integer id);

    List<AccountType> getAccountTypes();
}
