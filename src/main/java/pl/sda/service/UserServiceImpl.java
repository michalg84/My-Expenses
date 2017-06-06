package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import pl.sda.dto.AccountDto;
import pl.sda.dto.UserDto;
import pl.sda.model.Account;
import pl.sda.model.Category;
import pl.sda.model.Role;
import pl.sda.model.User;
import pl.sda.repository.AccountRepository;
import pl.sda.repository.CategoryRepository;
import pl.sda.repository.RoleRepository;
import pl.sda.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Michał Gałka on 2017-04-07.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AccountService accountService;

    /**
     * Converts authenticated User to UserDto.
     *
     * @return UserDto of current loged User.
     */
    public UserDto getCurrentUserDto() {
        if (this.getCurrentUser() == null)
            return null;
        return convertUserToUserDto(this.getCurrentUser());
    }

    /**
     * Gets authenticated User username and gets his User object form DB.
     *
     * @return Authenticated User object.
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null) {
            return findUserByUsername(authentication.getName());
        }
        return null;
    }

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
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
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
        userDto.setRoles(user.getRoles());
        return userDto;
    }


    /**
     * Adds UserDto to Database.
     *
     * @param userDto User to be saved to database.
     */
    public void save(UserDto userDto) {
        User user = convertUserDtoToUser(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        user.setRoles(new HashSet<>());


        Role userRole = roleRepository.findOne(1);
        user.getRoles().add(userRole);

        List<Category> categories = categoryService.initialCategories(user);
        categoryRepository.save(categories);

        userRepository.save(user);
    }


    /**
     * Gets All UsersDto.
     *
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
     * Finds UserDto using username
     *
     * @param username Authenticated Users username.
     * @return UserDto.
     */
    public UserDto findUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return convertUserToUserDto(user);
    }

    /**
     * Gets acctual total acount balance for acctual user.
     *
     * @return Users account balance from all Accounts.
     */
    public BigDecimal getTotalBalance() {
        return accountRepository.getTotalBallance(getCurrentUser());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<AccountDto> getAccounts(User acctualUser) {
        List<Account> accounts = accountRepository.findAll(acctualUser);
        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account a : accounts) {
            AccountDto accountDto = accountService.convertAccountToAccountDto(a);
            accountDtos.add(accountDto);
        }
        return accountDtos;
    }

    @Override
    public FieldError checkIfSuchUserExists(UserDto userDto) {
        FieldError fieldError;
        if (userRepository.findByUsername(userDto.getUsername()) != null)
            return fieldError = new FieldError("userDto", "username", "Such user already exists");
        if (userRepository.findUserByMail(userDto.getMail()) != null)
            return fieldError = new FieldError("userDto", "mail", "Such e-mail already exists.");
        return null;
    }
}
