package de.codecentric.demo.guestbook.infrastructure.thirdParty;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.stereotype.Component;

import de.codecentric.demo.guestbook.domain.GuestbookEntry;
import de.codecentric.demo.guestbook.domain.GuestbookMailClient;

@Component
public class SpringMailClient implements GuestbookMailClient{

    private final KeycloakRestTemplate template;

    public SpringMailClient(KeycloakRestTemplate template) {
        this.template = template;
    }

    @Override
    public Boolean sendMail(GuestbookEntry entry) {
        String endpoint = "http://localhost:8082/mail";
        return template.postForObject(endpoint, entry, Boolean.class).booleanValue();
    }
}
