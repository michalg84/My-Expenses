package pl.sda.service;

import java.math.BigDecimal;
import java.util.List;
import pl.sda.dto.MoveCashDto;
import pl.sda.dto.TransactionDto;
import pl.sda.model.Transaction;
import pl.sda.service.user.UserDto;

public interface TransactionService {
    BigDecimal getTransactionSum(List<Transaction> transactionList);

    List<BigDecimal> getTransactionsBalanceList(UserDto userDto);

    List<TransactionDto> getTransactionsWithBalance();

    void addTransaction(TransactionDto transactionDto);

    void removeById(Integer transId);

    List<Transaction> getAllTransactions();

    void moveBetweenAccounts(MoveCashDto moveCashDto);

}
