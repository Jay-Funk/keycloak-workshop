package de.codecentric.demo.guestbook.infrastructure.environment.spring.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanKeycloakConfiguration {
    /**
     * Sets keycloaks config resolver to use springs application.properties instead of keycloak.json (which is standard)
     * @return
     */
    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}