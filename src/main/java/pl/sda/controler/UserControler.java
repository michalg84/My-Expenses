package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.MessageDto;
import pl.sda.dto.UserDto;
import pl.sda.model.User;
import pl.sda.service.UserService;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/user")
public class UserControler {

    @Autowired
    private UserService userService;

    @GetMapping("/userAccount/{id}")
    public ModelAndView userAccount(@PathVariable("id") Integer id, ModelMap modelMap) {
        UserDto userDto = userService.findById(id);
        modelMap.addAttribute("userDto", userDto);
        return new ModelAndView("user/userAccount", modelMap);
    }

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
        //Sprawdzenie czy hasło i potwierdznie hasła podane przy rejestracji są różne.
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            modelMap.addAttribute(userDto);
            //TODO: wyświetlić komunikat o błędnym haśle.
            return new ModelAndView("redirect:/regiser", modelMap);
        } else {
            //Jeśli hasła się zgadzają, sprawdzamy cyz nie istnieje już taki użytkownik w bazie.
            if (userService.checkIfSuchUserExistsInDb(userDto)) {
                //todo: wyświetlić komunikat że taki użytkonik już istnieje

                modelMap.addAttribute("userDto", userDto);
                return new ModelAndView("redirect:/regiser", modelMap);

            } else {
                //Zapis odo Bbazy.
                userService.save(userDto);
                //Pobranie użytkownika z bazy wraz z jego Id.
                userDto = userService.getUserDtoByMail(userDto.getMail());
                modelMap.addAttribute(userDto);
                return new ModelAndView("user/userAccount/", modelMap);
            }
        }
    }
}
