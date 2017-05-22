package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.MessageDto;
import pl.sda.dto.UserDto;
import pl.sda.service.UserService;

import javax.validation.Valid;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;
    private final static String LOGIN_PAGE = "login";
    private final static String REGISTER_PAGE = "register";

    private final Logger log = LoggerFactory.getLogger(HomeController.class);


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

        MessageDto logoutMsg = new MessageDto("You've been succesfuly logout.");
        modelMap.addAttribute("message", logoutMsg);
        return LOGIN_PAGE;
    }



    @RequestMapping("/login/error")
    public ModelAndView login(ModelMap modelMap) {
        MessageDto loginError = new MessageDto("Wrong username or password");
        modelMap.addAttribute("message", loginError);
        return new ModelAndView("/login", modelMap);
    }

    /**
     * Opens register page.
     *
     * @return regiser page with userDto object.
     */
    @GetMapping("register")
    public ModelAndView regiserPage(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView(REGISTER_PAGE, modelMap);
    }

    @PostMapping("register")
    public String addUser(@ModelAttribute(name = "userDto") @Valid UserDto userDto,
                          BindingResult result) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            FieldError fieldError = new FieldError("userDto", "confirmPassword", "password doesn't match");
            result.addError(fieldError);
        }
        if (!result.hasErrors()) {
            userService.save(userDto);
            return "redirect:/user/account/";
        } else {
            System.out.println("błąd");
        }
        return REGISTER_PAGE;
    }

    /**
     * Login method.
     *
     * @param modelMap main page model
     * @return model and view of main page.
     */
//    @PostMapping(value = "login")
//    public String login(@ModelAttribute(name = "userDto") @Valid UserDto userDto, BindingResult result, ModelMap modelMap) {
//        if (!result.hasErrors()) {
//            userDto = userService.findUserDtoByUsername(userDto.getUsername());
//            modelMap.addAttribute("userDto", userDto);
//            return "redirect:/user/account/";
//        }
//
//        return LOGIN_PAGE;
//    }

}
