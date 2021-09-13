package be.fooda.backend.contact.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactResponse {

    Long id;

    UserResponse user;

    String firstName;

    String familyName;

    String companyName;

    String mobilePhoneNumber;

    String linePhoneNumber;

    String email;

    Boolean canCall;

    Boolean isActive;

    String title;

    Boolean isCurrent;

    Timestamp creationTime;

    Timestamp updateTime;
}