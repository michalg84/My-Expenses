package pl.sda.service;

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

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    /**
     * Get acctual User account list.
     *
     * @return List of User AccountDto's
     */
    public List<AccountDto> getAccounts() {
        List<Account> accounts = accountRepository.findAll(userService.getCurrentUser());
        List<AccountDto> accountDtos = new ArrayList<>();

        for (Account a : accounts) {
            AccountDto accountDto = convertAccountToAccountDto(a);
            accountDtos.add(accountDto);
        }
        return accountDtos;
    }


    /**
     * Add new acount to User account list.
     *
     * @param accountDto AccountDto
     */
    public void addAccount(AccountDto accountDto) {
        accountDto.setCreationDate(new Date());
        accountRepository.save(convertAccountDtoToAccount(accountDto));
        messageService.addSuccessMessage("Account added !");

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
    public Account convertAccountDtoToAccount(AccountDto newAccount) {
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
    public AccountDto convertAccountToAccountDto(Account account) {
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
