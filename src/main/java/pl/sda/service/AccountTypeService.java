package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.model.AccountType;
import pl.sda.repository.AccountRepository;
import pl.sda.repository.AccountTypeRepository;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-19.
 */
@Service
public class AccountTypeService {
    @Autowired
    AccountTypeRepository accountTypeRepository;
    public List<AccountType> getAccountTypes() {
        return accountTypeRepository.findAll();
    }
}
