package dev.galka.account.domain;

import dev.galka.account.adapters.out.AccountDbEntity;
import dev.galka.service.account.AccountDto;
import dev.galka.service.user.AuthUserProvider;

import java.util.List;
import java.util.stream.Collectors;

final class AccountProvider {

    private final AuthUserProvider authUserProvider;
    private final AccountFindPort accountFindPort;
    private final AccountMapper mapper = new AccountMapper();

    AccountProvider(AuthUserProvider authUserProvider, AccountFindPort accountFindPort) {
        this.authUserProvider = authUserProvider;
        this.accountFindPort = accountFindPort;
    }


    List<AccountDto> find() {
        final Integer id = authUserProvider.authenticatedUser().getId();
        final List<AccountDbEntity> accountDbEntities = accountFindPort.findByUserId(id);
        return accountDbEntities.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}
