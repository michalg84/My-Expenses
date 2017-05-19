package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.UserDto;
import pl.sda.model.Account;
import pl.sda.model.User;
import pl.sda.repository.AccountRepository;

import java.util.List;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAccounts(User user){
        return accountRepository.findAll(user);
    }

}
