package pl.sda.service.account;

import java.math.BigDecimal;
import java.util.List;
import pl.sda.dto.TransactionDto;
import pl.sda.model.AccountType;
import pl.sda.model.User;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
public interface AccountService {

    void addAccount(AccountDto accountDto);

    void updateAccountBalance(TransactionDto transactionDto);

    BigDecimal getTotalBalance();

    List<AccountDto> getAccounts(Integer id);

    List<AccountType> getAccountTypes();
}
