package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.UserDto;
import pl.sda.service.UserService;

import javax.validation.Valid;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final static String LOGIN_PAGE = "login";
    private final static String REGISTER_PAGE = "register";
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private UserService userService;

    /**
     * Opens login page.
     *
     * @param modelMap
     * @return
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
     * @return regiser page with userDto object.
     */
    @GetMapping("register")
    public ModelAndView regiserPage(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView(REGISTER_PAGE, modelMap);
    }

    @PostMapping("register")
    public String addUser(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult result) {
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
            log.debug("Errors regisered: " + result.getAllErrors());
        }
        return REGISTER_PAGE;
    }
}
