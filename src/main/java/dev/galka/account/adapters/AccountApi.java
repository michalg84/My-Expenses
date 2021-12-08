package dev.galka.account.adapters;

import dev.galka.service.account.AccountDto;

public final class AccountApi {

    private final UserCreator creator;

    public AccountApi(UserCreator creator) {
        this.creator = creator;
    }

    public void createAccount(AccountDto dto) {
        creator.create(dto);
    }
}
