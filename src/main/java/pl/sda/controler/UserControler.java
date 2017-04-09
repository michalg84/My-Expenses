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
     * @param userDto
     * @return user acount view page.
     */
    @PostMapping("/addUser")
    public ModelAndView view(@ModelAttribute(name = "userDto") UserDto userDto) {
        System.out.println(userDto);
        userService.save(userDto);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(userDto);

        return new ModelAndView("TODO", modelMap);
    }
}
