package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.UserService.UserService;
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
     * Main page view.
     * @param modelMap main page model
     * @return model and view of main page.
     */
    @GetMapping()
    public ModelAndView index(ModelMap modelMap) {
        modelMap.addAttribute("userDto", new UserDto());
        return new ModelAndView("home/index", modelMap);
        //todo przerobić index.html na dodaj użytkownika
    }

    @PostMapping("/addUser")
    public ModelAndView view(@ModelAttribute("name = userDto") UserDto userDto) {
        System.out.println(userDto);
        userService.save(userDto);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(userDto);

        return new ModelAndView("/user/view", modelMap);
    }

}
