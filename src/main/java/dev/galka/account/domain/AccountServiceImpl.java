package dev.galka.account.domain;

import dev.galka.account.dto.AccountDto;
import dev.galka.account.inout.AccountDbEntity;
import dev.galka.account.inout.AccountRepository;
import dev.galka.account.inout.AccountService;
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
    private final AccountMapper mapper = new AccountMapper();

    @Override
    public List<AccountDto> getAccounts() {
        try {
            final Integer id = authUserProvider.authenticatedUser().getId();
            return accountRepository.findByUserId(id)
                    .stream()
                    .map(mapper::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn(String.format("No accounts fount for user %s", authUserProvider.authenticatedUser().getUsername()));
        }
        return Collections.emptyList();
    }

    public void updateAccountBalance(TransactionDto transactionDto) {
        AccountDbEntity account = accountRepository.getById(transactionDto.getAccount().getId());
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
