
package be.fooda.backend.contact.service.mapper;

import be.fooda.backend.contact.model.entity.ContactEntity;
import be.fooda.backend.contact.model.dto.CreateContactRequest;
import be.fooda.backend.contact.model.dto.UpdateContactRequest;
import be.fooda.backend.contact.model.dto.ContactResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactMapper {

    ContactEntity toEntity(CreateContactRequest source);

    ContactEntity toEntity(UpdateContactRequest source, @MappingTarget ContactEntity target);

    CreateContactRequest toCreateRequest(ContactEntity source);

    UpdateContactRequest toUpdateRequest(ContactEntity source, @MappingTarget UpdateContactRequest target);

    ContactResponse toResponse(ContactEntity source);

    List<ContactResponse> toResponses(List<ContactEntity> source);
}
