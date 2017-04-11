package pl.sda.service;

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

    /**
     * Saves User o DB unlees such user exists.
     *
     * @param userDto UserDto to be save in DB.
     * @return true if such user exists, false if does not.
     */
    public boolean save(UserDto userDto) {
        User user = convertUserDtoToUser(userDto);
        user.setPassword(PasswordService.hashPassword(user.getPassword()));
        userRepository.save(user);
        System.out.println("Nie ma takiego użytkownika w bazie. Dodano użytkownika do bazy.");
        return false;
    }

    /**
     * Checks if password is equal with password in DB.
     *
     * @param mail     used to find user in DB.
     * @param password give by user during loging in.
     * @return True if password is equal, otherwise returns false.
     */
    public boolean checkPassword(String mail, String password) {
        User user = userRepository.getUserByMail(mail);
        return PasswordService.checkPassword(password, user.getPassword());

    }

    /**
     * Finds a User in DB by UserDto e-mail.
     *
     * @param mail
     * @return UserDto from DB or NullPointerException.
     */
    public UserDto getUserDtoByMail(String mail) {
        if (mail == null || mail.isEmpty())
            throw new NullPointerException("Mail nie może być pusty.");
        else {
            try {
                UserDto userDto = convertUserToUserDto(userRepository.getUserByMail(mail));
                return userDto;
            } catch (NullPointerException e) {
                throw new NullPointerException("Nie znaleziono takiego użytkownika w bazie");
            }
        }
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
        if (user == null)
            return null;
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setMail(user.getMail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    /**
     * Checks if user with such e-mail exists in DB.
     *
     * @param userDto
     * @return false if User with such e-mail doesn't exists. true is exists.
     */
    public boolean checkIfSuchUserExistsInDb(UserDto userDto) {
        return getUserDtoByMail(userDto.getMail()) != null;
    }

    public UserDto findById(Integer id) {
        try {
            UserDto userDto = convertUserToUserDto(userRepository.getOne(id));
            return userDto;
        } catch (NullPointerException e) {
            throw new NullPointerException("Nie znaleziono takiego użytkownika po Id");
        }
    }
}
