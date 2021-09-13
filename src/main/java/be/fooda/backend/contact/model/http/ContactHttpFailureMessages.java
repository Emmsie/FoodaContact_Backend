package be.fooda.backend.contact.model.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContactHttpFailureMessages {
    CONTACT_ALREADY_EXISTS("The contact already exists"),
    CONTACT_DOES_NOT_EXIST("This contact does not exist") ,
    NO_CONTACTS_FOUND ("Can't find contacts");

    private final String description;
}
