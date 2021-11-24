package pl.sda.service.account;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.TransactionDto;
import pl.sda.model.Account;
import pl.sda.model.AccountType;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
class AccountServiceImpl implements AccountService {
    private static final Logger logger_ = Logger.getLogger(AccountServiceImpl.class);

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<AccountDto> getAccounts(Integer userId) {
        try {
            return accountRepository.findByUserId(userId)
                    .stream()
                    .map(AccountMapper::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger_.warn(String.format("No accounts fount for user %s", userService.getCurrentUser().getUsername()));
        }
        return Collections.emptyList();
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

    @Override
    public BigDecimal getTotalBalance() {
        return accountRepository.getTotalBalance(userService.getCurrentUser());
    }

    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }
}
