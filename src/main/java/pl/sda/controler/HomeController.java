package pl.sda.controler;

import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.service.user.UserDto;
import pl.sda.service.user.UserService;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private static final String LOGIN_PAGE = "login";
    private static final String REGISTER_PAGE = "register";
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private UserService userService;

    /**
     * Opens login page.
     *
     * @param modelMap model map
     * @return to login page
     */
    @GetMapping("login")
    public String loginPage(ModelMap modelMap) {
        return LOGIN_PAGE;
    }

    @RequestMapping("login?logout")
    public String logoutPage(ModelMap modelMap) {

        return LOGIN_PAGE;
    }



    @RequestMapping("login/error")
    public ModelAndView login(ModelMap modelMap) {
        return new ModelAndView("/login", modelMap);
    }

    /**
     * Opens register page.
     *
     * @return register page with userDto object.
     */
    @GetMapping("register")
    public ModelAndView registerPage(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView(REGISTER_PAGE, modelMap);
    }

    @PostMapping("register")
    public String addUser(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult result) {
        //TODO - move validation to service
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            FieldError fieldErrorPassword = new FieldError("userDto", "confirmPassword", "password doesn't match");
            result.addError(fieldErrorPassword);
            log.info("Password doesn't match ConfirmedPassword");
        }
        FieldError fieldErrorExists = userService.checkIfSuchUserExists(userDto);
        if (fieldErrorExists != null)
            result.addError(fieldErrorExists);
        if (!result.hasErrors()) {
            userService.save(userDto);
            return "redirect:/user/account";
        } else {
            final String errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::toString)
                    .collect(Collectors.joining("."));
            log.debug("Errors registered: {}", errors);
        }
        return REGISTER_PAGE;
    }
}
