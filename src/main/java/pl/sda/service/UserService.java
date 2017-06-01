package pl.sda.service;

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

    User convertUserDtoToUser(UserDto userDto);

    UserDto convertUserToUserDto(User user);

    void save(UserDto userDto);

    UserDto getCurrentUserDto();

    List<UserDto> getAll();

    UserDto findUserDtoByUsername(String username);

    BigDecimal getTotalBalance();

    User findUserByUsername(String username);

    List<AccountDto> getAccounts(User acctualUser);

}
