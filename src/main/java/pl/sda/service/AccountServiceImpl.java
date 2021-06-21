package pl.sda.service;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;
import pl.sda.mapper.AccountMapper;
import pl.sda.model.Account;
import pl.sda.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger_ = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    public List<AccountDto> getUserAccounts() {
        List<AccountDto> accountDtoList = new ArrayList<>();
        try {
            return getAccountsDto(AccountMapper::map); //todo
        } catch (Exception e) {
            logger_.warn(String.format("No accounts fount for user %s", userService.getCurrentUser().getUsername()));
        }
        return Collections.emptyList();
    }

    private List<AccountDto> getAccountsDto(Function<Account, AccountDto> mapper) {
        return accountRepository.findAll(userService.getCurrentUser()).stream()
                .map(mapper)
                .collect(Collectors.toList());
    }


    public void addAccount(AccountDto accountDto) {
        accountDto.setCreationDate(new Date());
        Account account = AccountMapper.map(accountDto);
        account.setUser(userService.getCurrentUser());
        try {
            accountRepository.save(account);
            messageService.addSuccessMessage("Account added !");
        } catch (Exception e) {
            messageService.addErrorMessage(String.format("Failed to add account %s", accountDto.getName()));
        }

    }

    public void updateAccountBalance(TransactionDto transactionDto) {
        Account account = accountRepository.getOne(transactionDto.getAccount().getId());
        account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        accountRepository.save(account);
    }
   }
