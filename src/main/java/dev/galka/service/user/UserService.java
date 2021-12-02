package dev.galka.service.user;

import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    UserDto getCurrentUserDto();

    Integer getCurrentUserId();

    void save(UserDto userDto);

    List<UserDto> getAll();

    BigDecimal getTotalBalance();

    FieldError checkIfSuchUserExists(UserDto userDto);
}

