package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
     * @param modelMap main page model
     * @return model and view of main page.
     */
    @PostMapping("signIn")
    public ModelAndView signIn(@ModelAttribute(name = "userDto") UserDto userDto, ModelMap modelMap){


        return new ModelAndView("/user/userAccount", modelMap);
    }

    /**
     * redirects to page to add new user.
     * @param modelMap
     * @return
     */
    @RequestMapping("regiser")
    public ModelAndView regiser(ModelMap modelMap){
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView("/user/addUser", modelMap);
    }



}
