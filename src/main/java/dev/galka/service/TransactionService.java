package dev.galka.service;

import dev.galka.dto.MoveCashDto;
import dev.galka.dto.TransactionDetailsDto;
import dev.galka.dto.TransactionDto;
import dev.galka.model.TransactionDbEntity;
import dev.galka.service.user.UserDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    BigDecimal getTransactionSum(List<TransactionDbEntity> transactionList);

    List<BigDecimal> getTransactionsBalanceList(UserDto userDto);

    List<TransactionDetailsDto> getTransactionsWithBalance();

    void addTransaction(TransactionDto transactionDto);

    void removeById(Integer transId);

    List<TransactionDbEntity> getAllTransactions();

    void moveBetweenAccounts(MoveCashDto moveCashDto);

}
