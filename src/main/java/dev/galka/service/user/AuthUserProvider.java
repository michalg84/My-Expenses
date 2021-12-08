package dev.galka.service.user;

import dev.galka.account.domain.User;
import org.springframework.security.core.Authentication;

public interface AuthUserProvider {
    Authentication getAuthentication();


    User authenticatedUser();
}
