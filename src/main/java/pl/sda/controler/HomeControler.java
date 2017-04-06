package pl.sda.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Controller
@RequestMapping("/")
public class HomeControler {

    @GetMapping
    public ModelAndView index(ModelMap modelMap) {

        return new ModelAndView("layout", modelMap);
    }
}
