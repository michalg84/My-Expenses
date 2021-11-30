package pl.sda.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.service.account.AccountDto;
import pl.sda.service.account.AccountService;
import pl.sda.service.user.UserDto;
import pl.sda.service.user.UserService;
import pl.sda.service.webnotification.MessageService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static pl.sda.service.user.url.HttpActions.ADD;
import static pl.sda.service.user.url.UserHttpUrls.ACCOUNT;
import static pl.sda.service.user.url.UserHttpUrls.USER;

@Controller
@RequestMapping(USER)
public class UserController {

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    @RequestMapping(ACCOUNT)
    public ModelAndView userAccount(ModelMap modelMap, HttpSession session) {
        UserDto userDto = userService.getCurrentUserDto();
        session.setAttribute("username", userDto.getUsername());
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("accounts",
                accountService.getAccounts(userService.getCurrentUserId()));
        modelMap.addAttribute("sum", userService.getTotalBalance());
        modelMap.addAttribute("newAccount", new AccountDto());
        modelMap.addAttribute("accountTypes", accountService.getAccountTypes());
        return new ModelAndView(USER + ACCOUNT, modelMap);
    }


    @PostMapping(ACCOUNT + ADD)
    public String addAccount(@ModelAttribute(name = "newAccount") @Valid AccountDto accountDto,
                             BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            accountService.addAccount(accountDto);
            return "redirect:/" + USER + ACCOUNT;
        }
        messageService.addErrorMessage("Error. Cannot add new account !" + result.getAllErrors());
        return "redirect:/" + USER + ACCOUNT;
    }
}
