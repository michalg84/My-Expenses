package dev.galka.service.account;

import dev.galka.account.adapters.AccountMapper;
import dev.galka.account.adapters.out.AccountRepository;
import dev.galka.account.domain.model.Account;
import dev.galka.account.domain.model.AccountType;
import dev.galka.dto.TransactionDto;
import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.webnotification.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
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
                    .map(AccountMapper::map)//TODO change access to default AccountMapper::map
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn(String.format("No accounts fount for user %s", authUserProvider.authenticatedUser().getUsername()));
        }
        return Collections.emptyList();
    }

    public void updateAccountBalance(TransactionDto transactionDto) {
        Account account = accountRepository.getById(transactionDto.getAccount().getId());
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
