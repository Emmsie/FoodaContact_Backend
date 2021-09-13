package be.fooda.backend.contact.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContactHttpSuccessMessages {
    CONTACT_CREATED("The contact is successfully created"),
    CONTACT_UPDATED("The contact is successfully updated"),
    CONTACT_DELETED("The contact is successfully deleted"),
    CONTACT_EXISTS("The contact exists");

    private final String description;
}
