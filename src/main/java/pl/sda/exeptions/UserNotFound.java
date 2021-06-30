package pl.sda.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Wrong username or password")
public class UserNotFound extends RuntimeException {
    public UserNotFound(String msg) {
        super(msg);
    }

}
