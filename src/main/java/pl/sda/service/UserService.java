package pl.sda.service;

import org.springframework.validation.FieldError;
import pl.sda.dto.AccountDto;
import pl.sda.dto.UserDto;
import pl.sda.model.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
public interface UserService {
    User getCurrentUser();

      void save(UserDto userDto);

    UserDto getCurrentUserDto();

    List<UserDto> getAll();

    UserDto findUserDtoByUsername(String username);

    BigDecimal getTotalBalance();

    User findUserByUsername(String username);

    List<AccountDto> getAccounts(User acctualUser);

    FieldError checkIfSuchUserExists(UserDto userDto);
}

