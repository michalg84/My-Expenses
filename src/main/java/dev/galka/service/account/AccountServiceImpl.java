package dev.galka.service.account;

import dev.galka.dto.TransactionDto;
import dev.galka.model.Account;
import dev.galka.model.AccountType;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
class AccountServiceImpl implements AccountService {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTypeRepository accountTypeRepository;
    @Autowired
    private AuthUserProvider authUserProvider;

    @Override
    public List<AccountDto> getAccounts() {
        try {
            final Integer id = authUserProvider.authenticatedUser().getId();
            return accountRepository.findByUserId(id)
                    .stream()
                    .map(AccountMapper::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn(String.format("No accounts fount for user %s", authUserProvider.authenticatedUser().getUsername()));
        }
        return Collections.emptyList();
    }


    public void addAccount(AccountDto accountDto) {
        accountDto.setCreationDate(new Date());
        Account account = AccountMapper.map(accountDto);
        account.setUser(authUserProvider.authenticatedUser());
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
        return accountRepository.getTotalBalance(authUserProvider.authenticatedUser());
    }

    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }
}
