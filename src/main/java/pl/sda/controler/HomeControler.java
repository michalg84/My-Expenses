package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.MessageDto;
import pl.sda.service.UserService;
import pl.sda.dto.UserDto;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Controller
@RequestMapping("/")
public class HomeControler {

    @Autowired
    private UserService userService;


    /**
     * Opens Sign In page.
     *
     * @param modelMap
     * @return
     */
    @GetMapping()
    public ModelAndView index(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView("home/index", modelMap);
    }


    /**
     * Checks password and loggs in.
     *
     * @param modelMap main page model
     * @return model and view of main page.
     */
    @PostMapping("signIn")
    public ModelAndView signIn(@ModelAttribute(name = "userDto") UserDto userDto, ModelMap modelMap) {
        boolean ifPasswordIsCorrect = userService.checkPassword(userDto.getMail(), userDto.getPassword());
        if (ifPasswordIsCorrect) {
            userDto = userService.getUserDtoByMail(userDto.getMail());
            modelMap.addAttribute("userDto", userDto);
            return new ModelAndView("redirect:/user/userAccount/" + userDto.getId(), modelMap);
        } else {
                //todo dodać wiadomość błędu
        }
        return new ModelAndView("redirect:/", modelMap);
    }

    /**
     * Redirects to page to add new user.
     *
     * @return regiser page with userDto object.
     */
    @RequestMapping("regiser")
    public ModelAndView regiser(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView("/user/addUser", modelMap);
    }


}
