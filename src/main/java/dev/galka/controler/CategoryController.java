package dev.galka.controler;

import dev.galka.dto.CategoryDto;
import dev.galka.service.CategoryService;
import dev.galka.service.webnotification.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    public static final String USER_TRANSACTIONS = "transaction/list";
    private final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MessageService messageService;

    @PostMapping("add")
    public String addNewCategory(@ModelAttribute("newCategory") @Valid CategoryDto categoryDto, BindingResult result) {
        if (result.hasErrors()) {
            addErrorMessages(result);
            return "redirect:/" + USER_TRANSACTIONS;
        } else {
            categoryService.add(categoryDto);
            return "redirect:/" + USER_TRANSACTIONS;
        }
    }

    private void addErrorMessages(Object arg) {
        if (arg instanceof BindingResult) {
            BindingResult result = (BindingResult) arg;
            result.getAllErrors().forEach(e -> messageService.addErrorMessage(e.getDefaultMessage()));
        } else if (arg instanceof String) {
            messageService.addErrorMessage((String) arg);
        } else {
            log.error("Invalid argument in {} for '{}'", this.getClass().getName(), arg);
        }
    }
}
