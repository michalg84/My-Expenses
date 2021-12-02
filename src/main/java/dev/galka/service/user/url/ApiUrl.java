package dev.galka.service.user.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public final class ApiUrl {
    @Value("server.servlet.context-path")
    String API;

}
