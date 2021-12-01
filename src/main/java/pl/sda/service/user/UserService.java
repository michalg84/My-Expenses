package pl.sda.service.user;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.validation.FieldError;
import pl.sda.model.User;

public interface UserService {

    UserDto getCurrentUserDto();

    Integer getCurrentUserId();

    void save(UserDto userDto);

    List<UserDto> getAll();

    BigDecimal getTotalBalance();

    FieldError checkIfSuchUserExists(UserDto userDto);
}

