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
import pl.sda.service.CategoryService;
import pl.sda.service.webnotification.MessageService;

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
