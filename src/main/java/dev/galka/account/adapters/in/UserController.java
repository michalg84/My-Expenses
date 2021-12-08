package dev.galka.account.adapters.in;

import dev.galka.account.adapters.AccountApi;
import dev.galka.service.account.AccountDto;
import dev.galka.service.account.AccountService;
import dev.galka.service.user.UserDto;
import dev.galka.service.user.UserService;
import dev.galka.service.user.url.HttpActions;
import dev.galka.service.user.url.UserHttpUrls;
import dev.galka.service.webnotification.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(UserHttpUrls.USER)
class UserController {

    @Autowired
    AccountService accountService;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    AccountApi accountApi;

    @RequestMapping(UserHttpUrls.ACCOUNT)
    public ModelAndView userAccount(ModelMap modelMap, HttpSession session) {
        UserDto userDto = userService.getCurrentUserDto();
        session.setAttribute("username", userDto.getUsername());
        modelMap.addAttribute("userDto", userDto);
        modelMap.addAttribute("accounts",
                accountService.getAccounts());
        modelMap.addAttribute("sum", userService.getTotalBalance());
        modelMap.addAttribute("newAccount", new AccountDto());
        modelMap.addAttribute("accountTypes", accountService.getAccountTypes());
        return new ModelAndView(UserHttpUrls.USER + UserHttpUrls.ACCOUNT, modelMap);
    }


    @PostMapping(UserHttpUrls.ACCOUNT + HttpActions.ADD)
    public String addAccount(@ModelAttribute(name = "newAccount") @Valid AccountDto accountDto,
                             BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            accountApi.createAccount(accountDto);
            return "redirect:" + UserHttpUrls.USER + UserHttpUrls.ACCOUNT;
        }
        messageService.addErrorMessage("Error. Cannot add new account !" + result.getAllErrors());
        return "redirect:" + UserHttpUrls.USER + UserHttpUrls.ACCOUNT;
    }
}
