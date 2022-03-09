package dev.galka.config;

import dev.galka.service.user.AuthUserProvider;
import dev.galka.service.user.AuthUserProviderImpl;
import dev.galka.service.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Config {
    @Bean
    @Lazy
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserRepository userRepository;

    @Bean
    AuthUserProvider authUserProvider() {
        return new AuthUserProviderImpl(userRepository);
    }
}
