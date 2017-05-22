package pl.sda.controler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.exeptions.UserNotFoundInDB;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorController {

    private static Logger log = LoggerFactory.getLogger(ErrorController.class);



//    @ExceptionHandler(UserNotFoundInDB.class)
//    public ModelAndView megaExceptionHandler(HttpServletRequest req, UserNotFoundInDB e) {
//
//        log.error("Request: " + req.getRequestURL() + ", raised " + e);
//
//        ModelAndView mvn = new ModelAndView();
//        mvn.addObject("exception", e);
//        mvn.addObject("url", req.getRequestURL());
//        mvn.setViewName("/error");
//        return mvn;
//    }
//
//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String exception(final Throwable throwable, final Model model) {
//        log.error("Exception during execution of SpringSecurity application", throwable);
//        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }

}