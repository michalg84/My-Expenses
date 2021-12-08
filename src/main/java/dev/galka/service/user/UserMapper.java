package dev.galka.service.user;

import dev.galka.account.domain.User;

public final class UserMapper {
    private UserMapper() {
    }

    public static User map(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setLogin(userDto.getLogin());
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        return user;
    }

    public static UserDto map(User user) {
        if (user == null)
            return null;
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setLogin(user.getLogin());
        userDto.setMail(user.getMail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
