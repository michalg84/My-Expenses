package pl.sda.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import pl.sda.model.Category;
import pl.sda.model.Role;
import pl.sda.model.User;
import pl.sda.service.CategoryService;
import pl.sda.service.account.AccountService;
import pl.sda.service.crypto.PasswordService;
import pl.sda.service.webnotification.MessageService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private AuthUserProvider authUserProvider;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    @Override
    public UserDto getCurrentUserDto() {
        if (authUserProvider.authenticatedUser() == null)
            return null;
        return UserMapper.map(authUserProvider.authenticatedUser());
    }

    @Override
    public Integer getCurrentUserId() {
        if (authUserProvider.authenticatedUser() == null)
            return null;
        return authUserProvider.authenticatedUser().getId();
    }


    public void save(UserDto userDto) {
        User user = UserMapper.map(userDto);
        user.setPassword(passwordService.encode(userDto.getPassword()));
        user.setRoles(new HashSet<>());


        Role userRole = roleService.get(1);
        user.getRoles().add(userRole);

        List<Category> categories = categoryService.initialCategories(user);
        categoryService.save(categories);

        userRepository.save(user);
    }

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

    public UserDto findUserDtoByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserMapper.map(user);
    }

    public BigDecimal getTotalBalance() {
        return accountService.getTotalBalance();
    }

    @Override
    public FieldError checkIfSuchUserExists(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null)
            return getFieldError("username", "Such user already exists");
        if (userRepository.findUserByMail(userDto.getMail()) != null)
            return getFieldError("mail", "Such e-mail already exists.");
        return null;
    }

    private FieldError getFieldError(String txt, String msg) {
        return new FieldError("userDto", txt, msg);
    }
}
