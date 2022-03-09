package dev.galka.service;

import dev.galka.account.domain.AccountApi;
import dev.galka.account.domain.User;
import dev.galka.account.inout.AccountDbEntity;
import dev.galka.account.inout.AccountRepository;
import dev.galka.dto.MoveCashDto;
import dev.galka.dto.TransactionDetailsDto;
import dev.galka.dto.TransactionDto;
import dev.galka.model.Category;
import dev.galka.model.CategoryMapper;
import dev.galka.model.TransactionDbEntity;
import dev.galka.repository.CategoryRepository;
import dev.galka.repository.TransactionRepository;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.user.UserDto;
import dev.galka.service.webnotification.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final AccountApi accountApi;
    private final AuthUserProvider authUserProvider;
    private final MessageService messageService;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    public BigDecimal getTransactionSum(List<TransactionDbEntity> transactionList) {
        Iterator<TransactionDbEntity> iter = transactionList.iterator();
        BigDecimal sum = new BigDecimal(0);
        while (iter.hasNext()) {
            sum = sum.add(iter.next().getAmount());
        }
        return sum;
    }


    public List<BigDecimal> getTransactionsBalanceList(UserDto userDto) {
        BigDecimal sum = accountApi.getTotalBalance();
        return userDto.getTransactionList()
                .stream()
                .map(t -> t.getAmount().add(sum))
                .collect(Collectors.toList());
    }

    private TransactionDetailsDto convertToDto(TransactionDbEntity t) {
        return TransactionDetailsDto.builder()
                .category(categoryMapper.map(t.getCategory()))
                .accountName(t.getAccount().getName())
                .amount(t.getAmount())
                .transDate(t.getTransDate())
                .comment(t.getComment())
                .id(t.getId())
                .build();


    }

    public List<TransactionDetailsDto> getTransactionsWithBalance() {
        List<TransactionDbEntity> transactions = getAllTransactions();
        List<TransactionDetailsDto> trnDtosList = new ArrayList<>();
        transactions.sort((t1, t2) -> t2.getTransDate().compareTo(t1.getTransDate()));
        for (TransactionDbEntity entity : transactions) {
            TransactionDetailsDto transactionDto = convertToDto(entity);
            trnDtosList.add(transactionDto);
        }
        BigDecimal accountsBalance = accountApi.getTotalBalance();
//        TODO review if needed //List<BigDecimal> balanceList = getBalanceList(transactions);

        for (int i = 0; i < trnDtosList.size(); i++) {
            BigDecimal balance;
            if (i == 0) {
                balance = accountsBalance;
            } else {
                balance = trnDtosList.get(i - 1).getBalance()
                        .subtract(trnDtosList.get(i - 1).getAmount());
            }
            trnDtosList.get(i).setBalance(balance);
        }

        return trnDtosList;
    }

    private List<BigDecimal> getBalanceList(List<TransactionDbEntity> transactions) {
        BigDecimal sum = accountApi.getTotalBalance();
        return transactions.stream()
                .map(t -> t.getAmount().add(sum))
                .collect(Collectors.toList());
    }

    public void addTransaction(TransactionDto transactionDto) {
        final BigDecimal balanceBefore = accountApi.getTotalBalance();

        final BigDecimal amount = transactionDto.getAmount();
        final BigDecimal total = balanceBefore.add(amount);
        transactionDto.setBalance(total);
        try {
            transactionRepository.save(convertToModel(transactionDto));
        } catch (Exception e) {
            messageService.addErrorMessage(String.format("Error saving transaction '%s' to database", transactionDto));
        }
        accountApi.updateAccountBalance(transactionDto);
        messageService.addSuccessMessage("Transaction added !");

    }


    private TransactionDbEntity convertToModel(TransactionDto transactionDto) {
        TransactionDbEntity transaction = new TransactionDbEntity();
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
            final TransactionDbEntity trn = transactionRepository.getById(transId);
            AccountDbEntity account = accountRepository.getById(trn.getAccount().getId());
            account.setBalance(account.getBalance().subtract(trn.getAmount()));
            accountRepository.save(account);
            transactionRepository.delete(trn);
        } catch (RuntimeException e) {
            messageService.addErrorMessage("Error removing transaction");
            log.error(e.getMessage(), e);
        }
        messageService.addSuccessMessage("Transactions was successfully removed");
    }

    public List<TransactionDbEntity> getAllTransactions() {
        return transactionRepository.findAllByUser(authUserProvider.authenticatedUser());
    }

    public void moveBetweenAccounts(MoveCashDto moveCashDto) {
        User user = authUserProvider.authenticatedUser();
        TransactionDbEntity out = new TransactionDbEntity();
        TransactionDbEntity in = new TransactionDbEntity();
        AccountDbEntity fromAccount = accountRepository.getById(moveCashDto.getFromAccountId());
        AccountDbEntity toAccount = accountRepository.getById(moveCashDto.getToAccountId());
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
