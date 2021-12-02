package dev.galka.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Wrong username or password")
public class UserNotFound extends RuntimeException {
    public UserNotFound(String msg) {
        super(msg);
    }

}
