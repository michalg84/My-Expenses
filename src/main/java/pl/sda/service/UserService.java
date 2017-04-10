package pl.sda.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.UserDto;
import pl.sda.model.User;
import pl.sda.repository.UserRepository;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean save(UserDto userDto) {
        if(getUserByLoginOrMail(userDto) != null) {
            System.out.println("Taki użytkownik już istnieje");
            return false;
        }
        else {

            User user = convertUserDtoToUser(userDto);
            user.setPassword(PasswordService.hashPassword(user.getPassword()));
            userRepository.save(user);
            System.out.println("Nie ma takiego użytkownika w bazie. Dodano użytkownika do bazy.");
            return true;
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    public UserDto getUserByLoginOrMail(UserDto userDto) {
        User user = convertUserDtoToUser(userDto);
        if (user.getLogin() != null || user.getLogin().isEmpty()) {
            return convertUserToUserDto(userRepository.getUserByLogin(user.getLogin()));
        } else if (user.getMail() != null || user.getMail().isEmpty()) {
            return convertUserToUserDto(userRepository.getUserByMail(user.getMail()));
        } else
            throw new NullPointerException("Lola login i mail nie mogą być puste.");

    }


    /**
     * Converts UserDto object to User object
     *
     * @param userDto class UserDto.
     * @return User Object.
     */
    private User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setLogin(userDto.getLogin());
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    /**
     * Converts User object to UserDto object.
     *
     * @param user class Object.
     * @return UserDto object. Note that UsedDto confirmPassword
     * is returned as null cause it's requiered only for registration.
     */
    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setMail(user.getMail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

}
