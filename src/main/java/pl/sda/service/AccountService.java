package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.AccountDto;
import pl.sda.dto.TransactionDto;
import pl.sda.model.Account;
import pl.sda.repository.AccountRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    public List<Account> getAccounts(){
        return accountRepository.findAll(userService.getAcctualUser());
    }

    public void addAccount(AccountDto accountDto) {
        accountDto.setUser(userService.getAcctualUser());
        accountDto.setCreationDate(new Date());
        accountRepository.save(convertAccountDtoToAccount(accountDto));
    }

    protected void updateAccountBalance(TransactionDto transactionDto) {
        Account account = accountRepository.getOne(transactionDto.getAccount().getId());
        account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        accountRepository.save(account);
    }
    /**
     * Converts AcountDto to Account.
     * @param newAccount
     * @return
     */
    private Account convertAccountDtoToAccount(AccountDto newAccount) {
        Account account = new Account();
        account.setAccountNumber(newAccount.getAccountNumber());
        account.setAccountType(newAccount.getAccountType());
        account.setBalance(newAccount.getBalance());
        account.setCreationDate(newAccount.getCreationDate());
        account.setName(newAccount.getName());
        account.setUser(newAccount.getUser());
        return account;

    }
}
