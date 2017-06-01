package pl.sda.service;

import pl.sda.model.AccountType;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-19.
 */
public interface AccountTypeService {
    List<AccountType> getAccountTypes();
}
