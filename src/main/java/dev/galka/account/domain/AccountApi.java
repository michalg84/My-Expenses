package dev.galka.account.domain;

import dev.galka.service.account.AccountDto;
import dev.galka.service.user.AuthUserProvider;

import java.util.List;

public final class AccountApi {

    private final AccountCreator creator;
    private final AccountProvider provider;


    public AccountApi(AuthUserProvider authUserProvider, AccountCreator creator, AccountProvider provider) {
        this.creator = creator;
        this.provider = provider;
    }

    public void createAccount(AccountDto dto) {
        creator.create(dto);
    }

    public List<AccountDto> find() {
        return provider.find();
    }

    ;

}
