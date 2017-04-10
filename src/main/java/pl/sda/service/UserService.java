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
        if (getUserDtoByMail(userDto.getMail()) != null) {
            System.out.println("Taki użytkownik już istnieje");
            //todo: wiadomość że taki użytkownik już istnieje.
            return true;
        } else {
            User user = convertUserDtoToUser(userDto);
            user.setPassword(PasswordService.hashPassword(user.getPassword()));
            userRepository.save(user);
            System.out.println("Nie ma takiego użytkownika w bazie. Dodano użytkownika do bazy.");
            return false;
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findOne(id);
    }

    public UserDto getUserDtoByMail(String mail) {
        if (mail == null || mail.isEmpty())
            throw new NullPointerException("Mail nie może być pusty.");
        else
            return convertUserToUserDto( userRepository.getUserByMail(mail));
    }


    public boolean checkPassword(String mail, String password) {
        User user;
        try {
            user = userRepository.getUserByMail(mail);
        } catch (NullPointerException e) {
            throw new NullPointerException("Nie ma takiego użytkownika  bazie");
        }
        String hashedPassword = PasswordService.hashPassword(password);
        if (hashedPassword.equals(user.getPassword()))
            return true;
        return false;

    }

    /**
     * Converts UserDto object to User object
     *
     * @param userDto class UserDto.
     * @return User Object.
     */
    private User convertUserDtoToUser(UserDto userDto) {
//        if (userDto == null) {
//            return null;
//        }
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
        if (user == null)
            return null;
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLogin(user.getLogin());
        userDto.setMail(user.getMail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
