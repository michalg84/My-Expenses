package dev.galka.service;

import dev.galka.dto.MoveCashDto;
import dev.galka.dto.TransactionDto;
import dev.galka.model.Transaction;
import dev.galka.service.user.UserDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    BigDecimal getTransactionSum(List<Transaction> transactionList);

    List<BigDecimal> getTransactionsBalanceList(UserDto userDto);

    List<TransactionDto> getTransactionsWithBalance();

    void addTransaction(TransactionDto transactionDto);

    void removeById(Integer transId);

    List<Transaction> getAllTransactions();

    void moveBetweenAccounts(MoveCashDto moveCashDto);

}
