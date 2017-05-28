package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.dto.CategoryDto;
import pl.sda.model.Category;
import pl.sda.service.*;

import javax.validation.Valid;

/**
 * Created by Michał Gałka on 2017-04-09.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    public static final String USER_TRANSACTIONS = "user/list";
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageService messageService;

    @PostMapping("add")
    public String addNewCategory(@ModelAttribute("newCategory") @Valid CategoryDto categoryDto, BindingResult result) {
        if (result.hasErrors()) {
            log.warn(categoryDto + " name : " + categoryDto.getName() + " is invalid");
            messageService.addWarnMessage("Category name has to be 3-40 signs.");
            return "redirect:/" + USER_TRANSACTIONS;
        } else
            categoryService.add(categoryDto);
        return "redirect:/" + USER_TRANSACTIONS;
    }

}
