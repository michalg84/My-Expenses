package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.MessageDto;
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
    public ModelAndView addUser(@ModelAttribute(name = "userDto") UserDto userDto,
                                @ModelAttribute(name = "messageDto") MessageDto messageDto) {
        System.out.println(userDto);
        ModelMap modelMap = new ModelMap();
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            modelMap.addAttribute(userDto);
            messageDto.setMsg("Podano błędne hasło");
            modelMap.addAttribute("messageDto", messageDto);
            //TODO: wyświetlić komunikat o błędnym haśle.
            System.out.println("wpisano błędne hasło");
            return new ModelAndView("redirect:/regiser", modelMap);
        } else {
            boolean suchUserExists = userService.save(userDto);
            if (suchUserExists) {
                //todo: wyświetlić komunikat że taki użytkonik już istnieje
                System.out.println("Taki użytkownik już istnieje");
                messageDto.setMsg("Taki login lub mail jest już zajęty");
                modelMap.addAttribute("messageDto", messageDto);
                modelMap.addAttribute("userDto", userDto);
                return new ModelAndView("redirect:/regiser", modelMap);

            } else {
                System.out.println("Użytkownik zapisany do bazy");
                userService.getUserDtoByMail(userDto.getMail());
                modelMap.addAttribute(userDto);
                return new ModelAndView("user/userAccount", modelMap);
            }
        }
    }
}
