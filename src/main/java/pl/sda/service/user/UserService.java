package pl.sda.service.user;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.validation.FieldError;
import pl.sda.model.User;

public interface UserService {
    User getCurrentUser();

    void save(UserDto userDto);

    UserDto getCurrentUserDto();

    Integer getCurrentUserId();

    List<UserDto> getAll();

    UserDto findUserDtoByUsername(String username);

    BigDecimal getTotalBalance();

    User findUserByUsername(String username);

    FieldError checkIfSuchUserExists(UserDto userDto);
}

