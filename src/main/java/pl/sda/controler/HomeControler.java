package pl.sda.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.dto.PlayerDto;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Controller
@RequestMapping("/")
public class HomeControler {

    /**
     * Main page view.
     * @param modelMap main page model
     * @return model and view of main page.
     */
    @GetMapping
    public ModelAndView index(ModelMap modelMap) {
        modelMap.addAttribute("playerDto", new PlayerDto());
        return new ModelAndView("home/index", modelMap);
    }

    @PostMapping("home/index")
    public ModelAndView view(@ModelAttribute("name = playerDto") PlayerDto playerDto) {
        System.out.println(playerDto);

        return new ModelAndView("/player/view", new ModelMap());
    }

}
