package pl.sda.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;
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
        List<Account> accounts = null;
        List<AccountDto> accountDtoList = new ArrayList<>();
        try {
            accounts = accountRepository.findAll(userService.getCurrentUser());
            for (Account a : accounts) {
                AccountDto accountDto = convertToModel(a);
                accountDtoList.add(accountDto);
            }
        } catch (Exception e) {
            logger_.warn(String.format("No accounts fount for user %s", userService.getCurrentUser().getUsername()));
        }
        return accountDtoList;
    }


    public void addAccount(AccountDto accountDto) {
        accountDto.setCreationDate(new Date());
        try {
            accountRepository.save(convertToDto(accountDto));
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

    /**
     * Converts AcountDto to Account.
     *
     * @param newAccount Account to be created.
     * @return Account converted from AccountDto.
     */
    public Account convertToDto(AccountDto newAccount) {
        Account account = new Account();
        account.setAccountNumber(newAccount.getAccountNumber());
        account.setAccountType(newAccount.getAccountType());
        account.setBalance(newAccount.getBalance());
        account.setCreationDate(newAccount.getCreationDate());
        account.setName(newAccount.getName());
        account.setUser(userService.getCurrentUser());
        return account;

    }

    /**
     * Converts Account to AccountDto.
     *
     * @param account to be converted.
     * @return AccountDto converted from Account
     */
    public AccountDto convertToModel(Account account) {
        AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setName(account.getName());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        accountDto.setCreationDate(account.getCreationDate());
        return accountDto;
    }


}
