package be.fooda.backend.contact.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Objects;


@Getter
@Setter
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name ="contacts")
@Indexed
public class ContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @IndexedEmbedded
    @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
    UserEntity user;

    @NotBlank(message = "Must enter a first name")
    String firstName;

    @NotBlank(message = "Must enter a family name")
    String familyName;

    String companyName;

    String mobilePhoneNumber;

    String linePhoneNumber;

    @Email(message = "Must enter a valid email")
    String email;

    Boolean canCall;

    Boolean isActive = Boolean.TRUE;

    String title;

    Boolean isCurrent =Boolean.FALSE;

    @CreationTimestamp
    Timestamp creationTime;

    @UpdateTimestamp
    Timestamp updateTime;

    public void setUser(UserEntity user) {
        user.setContact(this);
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactEntity)) return false;
        ContactEntity that = (ContactEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}