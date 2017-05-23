package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.dto.UserDto;
import pl.sda.model.Category;
import pl.sda.model.Role;
import pl.sda.model.User;
import pl.sda.repository.AccountRepository;
import pl.sda.repository.RoleRepository;
import pl.sda.repository.UserRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageService messageService;

    /**
     * Converts authenticated User to UserDto.
     *
     * @return
     */
    public UserDto getAcctualUserDto() {
        if (getAcctualUser() == null)
            return null;
        return convertUserToUserDto(getAcctualUser());
    }

    /**
     * Gets authenticated User username and gets his User object form DB.
     * @return Authenticated User object.
     */
    public User getAcctualUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            return findUserByUsername(authentication.getName());
        }
        return null;
    }

    public UserDto getUser() {
        return convertUserToUserDto(userRepository.getOne(1));
    }

    /**
     * Saves User o DB unlees such user exists.
     *
     * @param userDto UserDto to be save in DB.
     * @return true if such user exists, false if does not.
     */
//    public boolean save(UserDto userDto) {
//        User user = convertUserDtoToUser(userDto);
//        user.setPassword(PasswordService.hashPassword(user.getPassword()));
//        userRepository.save(user);
//        System.out.println("Nie ma takiego użytkownika w bazie. Dodano użytkownika do bazy.");
//        return false;
//    }





    /**
     * Converts UserDto object to User object
     *
     * @param userDto class UserDto.
     * @return User Object.
     */
    public User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setLogin(userDto.getLogin());
        user.setMail(userDto.getMail());
//        user.setPassword(userDto.getPassword());
        user.setAccounts(userDto.getAccounts());
        user.setTransactionList(userDto.getTransactionList());
        user.setRoles(userDto.getRoles());
        user.setCategories(userDto.getCategories());
        return user;
    }

    /**
     * Converts User object to UserDto object.
     *
     * @param user class Object.
     * @return UserDto object. Note that UsedDto confirmPassword
     * is returned as null cause it's requiered only for registration.
     */
    public UserDto convertUserToUserDto(User user) {
        if (user == null)
            return null;
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setLogin(user.getLogin());
        userDto.setMail(user.getMail());
//        userDto.setPassword(user.getPassword());
        userDto.setAccounts(user.getAccounts());
        userDto.setTransactionList(user.getTransactionList());
        userDto.setRoles(user.getRoles());
        userDto.setCategories(user.getCategories());
        return userDto;
    }



    /**
     * Adds UserDto to Database.
     * @param userDto User to be saved to database.
     */
    public void save(UserDto userDto) {
        User user = convertUserDtoToUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        user.setRoles(new HashSet<>());


        Role userRole = roleRepository.findOne(1);
        user.getRoles().add(userRole);

        user.setCategories(categoryService.initCategories());

        userRepository.save(user);
    }



    /**
     * Gets All UsersDto.
     * @return List of all UserDto.
     */
    public List<UserDto> getAll() {
        List<UserDto> usersDto = new ArrayList<>();

        try {
            List<User> users = userRepository.findAll();
            for (User u : users) {
                UserDto userDto = new UserDto();
                userDto.setUsername(u.getUsername());
                usersDto.add(userDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersDto;
    }

    /**
     * Finds User using username
     * @param username Authenticated Users username.
     * @return
     */
    public UserDto findUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return convertUserToUserDto(user);
    }

    /**
     * Gets acctual total acount balance for acctual user.
     * @return Users account balance from all Accounts.
     */
    public BigDecimal getTotalBalance() {
        return accountRepository.getTotalBallance(getAcctualUser());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}
