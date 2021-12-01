package pl.sda.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.sda.dto.MoveCashDto;
import pl.sda.dto.TransactionDto;
import pl.sda.model.Account;
import pl.sda.model.Category;
import pl.sda.model.Transaction;
import pl.sda.model.User;
import pl.sda.repository.CategoryRepository;
import pl.sda.repository.TransactionRepository;
import pl.sda.service.account.AccountRepository;
import pl.sda.service.account.AccountService;
import pl.sda.service.user.AuthUserProvider;
import pl.sda.service.user.UserDto;
import pl.sda.service.webnotification.MessageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AuthUserProvider authUserProvider;
    private final MessageService messageService;
    private final CategoryRepository categoryRepository;

    public BigDecimal getTransactionSum(List<Transaction> transactionList) {
        Iterator<Transaction> iter = transactionList.iterator();
        BigDecimal sum = new BigDecimal(0);
        while (iter.hasNext()) {
            sum = sum.add(iter.next().getAmount());
        }
        return sum;
    }


    public List<BigDecimal> getTransactionsBalanceList(UserDto userDto) {
        BigDecimal sum = accountService.getTotalBalance();
        return userDto.getTransactionList()
                .stream()
                .map(t -> t.getAmount().add(sum))
                .collect(Collectors.toList());
    }

    private TransactionDto convertToDto(Transaction t) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(t.getAmount());
        transactionDto.setAccount(t.getAccount());
        transactionDto.setId(t.getId());
        transactionDto.setComment(t.getComment());
        transactionDto.setTransDate(t.getTransDate());
        transactionDto.setCategory(t.getCategory());
        return transactionDto;

    }

    public List<TransactionDto> getTransactionsWithBalance() {
        List<Transaction> transactions = getAllTransactions();
        List<TransactionDto> transactionsDto = new ArrayList<>();
        transactions.sort((t1, t2) -> t2.getTransDate().compareTo(t1.getTransDate()));
        for (Transaction t : transactions) {
            TransactionDto transactionDto = convertToDto(t);
            transactionsDto.add(transactionDto);
        }
        BigDecimal accountsBalance = accountRepository.getTotalBalance(authUserProvider.authenticatedUser());
//        TODO review if needed //List<BigDecimal> balanceList = getBalanceList(transactions);

        for (int i = 0; i < transactionsDto.size(); i++) {
            BigDecimal balance;
            if (i == 0) {
                balance = accountsBalance;
            } else {
                balance = transactionsDto.get(i - 1).getBalance()
                        .subtract(transactionsDto.get(i - 1).getAmount());
            }
            transactionsDto.get(i).setBalance(balance);
        }

        return transactionsDto;
    }

    private List<BigDecimal> getBalanceList(List<Transaction> transactions) {
        BigDecimal sum = accountService.getTotalBalance();
        return transactions.stream()
                .map(t -> t.getAmount().add(sum))
                .collect(Collectors.toList());
    }

    public void addTransaction(TransactionDto transactionDto) {
        final BigDecimal balanceBefore = accountRepository.getTotalBalance(authUserProvider.authenticatedUser());

        final BigDecimal amount = transactionDto.getAmount();
        final BigDecimal total = balanceBefore.add(amount);
        transactionDto.setBalance(total);
        try {
            transactionRepository.save(convertToModel(transactionDto));
        } catch (Exception e) {
            messageService.addErrorMessage(String.format("Error saving transaction '%s' to database", transactionDto));
        }
        accountService.updateAccountBalance(transactionDto);
        messageService.addSuccessMessage("Transaction added !");

    }


    private Transaction convertToModel(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setAccount(transactionDto.getAccount());
        transaction.setId(transactionDto.getId());
        transaction.setComment(transactionDto.getComment());
        transaction.setTransDate(transactionDto.getTransDate());
        transaction.setUser(authUserProvider.authenticatedUser());

//        transaction.setBalance(transactionDto.getBalance());
        transaction.setCategory(transactionDto.getCategory());
        return transaction;
    }

    public void removeById(Integer transId) {
        try {
            final Transaction trn = transactionRepository.getById(transId);
            Account account = accountRepository.getById(trn.getAccount().getId());
            account.setBalance(account.getBalance().subtract(trn.getAmount()));
            accountRepository.save(account);
            transactionRepository.delete(trn);
        } catch (RuntimeException e) {
            messageService.addErrorMessage("Error removing transaction");
            log.error(e.getMessage(), e);
        }
        messageService.addSuccessMessage("Transactions was successfully removed");
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByUser(authUserProvider.authenticatedUser());
    }

    public void moveBetweenAccounts(MoveCashDto moveCashDto) {
        User user = authUserProvider.authenticatedUser();
        Transaction out = new Transaction();
        Transaction in = new Transaction();
        Account fromAccount = accountRepository.getById(moveCashDto.getFromAccountId());
        Account toAccount = accountRepository.getById(moveCashDto.getToAccountId());
        Category category = categoryRepository.findByUserAndName("MOVE BETWEEN ACCOUNTS", user);

        out.setAccount(fromAccount);
        out.setAmount(BigDecimal.ZERO.subtract(moveCashDto.getAmount()));
        out.setUser(user);
        out.setTransDate(moveCashDto.getDate());
        out.setComment(fromAccount.getName()
                + " -> " + toAccount.getName()
                + " (" + moveCashDto.getComment() + ")");
        out.setCategory(category);
        transactionRepository.save(out);
        fromAccount.setBalance(fromAccount.getBalance().add(out.getAmount()));
        accountRepository.save(fromAccount);


        in.setAccount(toAccount);
        in.setAmount(moveCashDto.getAmount());
        in.setUser(user);
        in.setTransDate(moveCashDto.getDate());
        in.setComment(toAccount.getName()
                + " -> " + fromAccount.getName()
                + " (" + moveCashDto.getComment() + ")");
        in.setCategory(category);
        transactionRepository.save(in);
        toAccount.setBalance(toAccount.getBalance().add(in.getAmount()));
        accountRepository.save(fromAccount);


        messageService.addSuccessMessage(moveCashDto.getAmount()
                + " PLN moved between accounts "
                + fromAccount.getName() + " -> "
                + toAccount.getName());

    }
}
