package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.UserDto;
import pl.sda.service.UserService;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/user")
public class UserControler {

    @Autowired
    private UserService userService;

    /**
     * Adds user to DB
     *
     * @param userDto is taken form addUser.html using @ModelAttribute.
     * @return user acount view page.
     */
    @PostMapping("/addUser")
    public ModelAndView addUser(@ModelAttribute(name = "userDto") UserDto userDto) {
        System.out.println(userDto);
        ModelMap modelMap = new ModelMap();
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            modelMap.addAttribute(userDto);
            //TODO: wyświetlić komunikat o błędnym haśle.
            return new ModelAndView("user/userAccount", modelMap);
        } else {
            userService.save(userDto);
            userDto = userService.getUserByLoginOrMail(userDto);

            modelMap.addAttribute(userDto);
        }
        return new ModelAndView("user/userAccount", modelMap);
    }
}
