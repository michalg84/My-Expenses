package pl.sda.service.user;

import org.springframework.security.core.Authentication;
import pl.sda.model.User;

public interface AuthUserProvider {
    Authentication getAuthentication();


    User authenticatedUser();
}
