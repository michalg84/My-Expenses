package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.TransactionDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Account;
import pl.sda.model.Transaction;
import pl.sda.model.User;
import pl.sda.repository.AccountRepository;
import pl.sda.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Michał Gałka on 2017-04-17.
 */
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private TransactionService transactionService;

    /**
     * Gets summary of all Transactions form List.
     * @param transactionList List of transactions do be added.
     * @return Sum of all transactions.
     */
    public BigDecimal getTransactionSum(List<Transaction> transactionList) {
        Iterator<Transaction> iter = transactionList.iterator();
        BigDecimal sum = new BigDecimal(0);
        while (iter.hasNext()) {
            sum = sum.add(iter.next().getAmount());
        }
        return sum;
    }


    public List<BigDecimal> getTransactionsBalanceList(UserDto userDto) {
        BigDecimal sum = userService.getTotalBalance();
        List<BigDecimal> list = new ArrayList<>();
        list = userDto.getTransactionList()
                .stream()
                .map(t -> t.getAmount().add(sum))
                .collect(Collectors.toList());
//        list.add(sum);
//        list.remove(0);
        return list;
    }

    /**
     * Converts Transaction to TransactionDto.
     * @param t Transaction object.
     * @return TransactionDto object.
     */
    private TransactionDto convertTransactionToTransactionDto(Transaction t) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAmount(t.getAmount());
            transactionDto.setAccount(t.getAccount());
            transactionDto.setId(t.getId());
            transactionDto.setComment(t.getComment());
            transactionDto.setTransDate(t.getTransDate());
            transactionDto.setCategory(t.getCategory());
        return transactionDto;

    }

    /**
     * Creates TransactionsDto List and sets up Transaction balance for each of them.
     * @return TransactionsDto List with balance.
     */
    public List<TransactionDto> getTransactionsWithBalance() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<TransactionDto> transactionsDto = new ArrayList<>();
        Collections
                .sort(transactions, (t1, t2) -> t2.getTransDate().compareTo(t1.getTransDate()));
        for (Transaction t : transactions) {
            TransactionDto transactionDto = convertTransactionToTransactionDto(t);
            transactionsDto.add(transactionDto);
        }
        BigDecimal accountsBalance = accountRepository.getTotalBallance(userService.getAcctualUser());
//        List<BigDecimal> balanceList = getBalanceList(transactions);

        for (int i = 0; i < transactionsDto.size(); i++) {
            BigDecimal balance = null;
            if (i == 0) {
                balance = accountsBalance;
            } else {
                balance = transactionsDto.get(i - 1).getBalance()
                        .subtract(transactionsDto.get(i-1).getAmount());
            }
            transactionsDto.get(i).setBalance(balance);
        }

        return transactionsDto;
    }

    /**
     * Creates a list of transactions balance.
     * @param transactions list of transactions.
     * @return Transactions balance List after every Transaction.
     */
    private List<BigDecimal> getBalanceList(List<Transaction> transactions) {
        BigDecimal sum = userService.getTotalBalance();
        return transactions.stream()
                .map(t -> t.getAmount().add(sum))
                .collect(Collectors.toList());
    }

    public void addTransaction(TransactionDto transactionDto) {
        User user = userService.getAcctualUser();
        transactionDto.setBalance(accountRepository
                .getTotalBallance(user)
                .add(transactionDto.getAmount()));
        transactionRepository.save(convertTransactionDtoToTransaction(transactionDto));
        accountService.updateAccountBalance(transactionDto);
        messageService.addSuccessMessage("Transaction added !");

    }



    private Transaction convertTransactionDtoToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setAccount(transactionDto.getAccount());
        transaction.setId(transactionDto.getId());
        transaction.setComment(transactionDto.getComment());
        transaction.setTransDate(transactionDto.getTransDate());
        transaction.setUser(userService.getAcctualUser());
//        transaction.setBalance(transactionDto.getBalance());
        transaction.setCategory(transactionDto.getCategory());
        return transaction;
    }

    public void removeById(Integer transId) {
        Transaction t = transactionRepository.findOne(transId);
        Account a = accountRepository.findOne(t.getAccount().getId());
        a.setBalance(a.getBalance().subtract(t.getAmount()));
        accountRepository.save(a);
        transactionRepository.delete(transId);
    messageService.addSuccessMessage("Transactions was succesfuly removed");
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByUser(userService.getAcctualUser());
    }
}
