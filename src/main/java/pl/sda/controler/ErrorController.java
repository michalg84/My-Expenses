package pl.sda.controler;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ErrorController {

    private ErrorController() {
    }
//    @ExceptionHandler(UserNotFound.class)
    //    public ModelAndView megaExceptionHandler(HttpServletRequest req, UserNotFound e) {
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