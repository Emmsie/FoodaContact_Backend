package be.fooda.backend.contact.service.flow;

import be.fooda.backend.contact.dao.ContactRepository;
import be.fooda.backend.contact.model.dto.ContactResponse;
import be.fooda.backend.contact.model.dto.CreateContactRequest;
import be.fooda.backend.contact.model.dto.UpdateContactRequest;
import be.fooda.backend.contact.model.entity.ContactEntity;
import be.fooda.backend.contact.model.http.ContactHttpFailureMessages;
import be.fooda.backend.contact.service.exception.ResourceAlreadyExistsException;
import be.fooda.backend.contact.service.exception.ResourceNotFoundException;
import be.fooda.backend.contact.service.mapper.ContactMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@RequiredArgsConstructor
@Service
public class ContactFlow {

    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;


    public void createContact(CreateContactRequest request){
        if(Objects.isNull(request)){
            throw new NullPointerException("There in no request in the body");
        }

        boolean exist = contactRepository.existsByEmail(request.getEmail());

        if(exist){
            throw new ResourceAlreadyExistsException(ContactHttpFailureMessages.CONTACT_ALREADY_EXISTS.getDescription());
        }

        ContactEntity entity = contactMapper.toEntity(request);

        contactRepository.save(entity);
    }

    @Transactional
    public void updateContact(Long id, UpdateContactRequest request){
        if(Objects.isNull(request)){
            throw new NullPointerException("There in no request in the body");
        }

        Optional<ContactEntity> contactToUpdate = contactRepository.findById(id);

        if(!contactToUpdate.isPresent()){
            throw new ResourceNotFoundException(ContactHttpFailureMessages.CONTACT_DOES_NOT_EXIST.getDescription());
        }

        contactMapper.toEntity(request, contactToUpdate.get());
    }

    public void deleteContactById(Long id){
        if (Objects.isNull(id)) {
            throw new NullPointerException("Contact id is required");
        }

        boolean exist = contactRepository.existsById(id);

        if(!exist){
            throw new ResourceNotFoundException(ContactHttpFailureMessages.CONTACT_DOES_NOT_EXIST.getDescription());
        }

        contactRepository.deleteById(id);
    }

    public List<ContactResponse> findAll(){
        List<ContactEntity> contacts =  contactRepository.findAll();
        return contactMapper.toResponses(contacts);
    }

    public ContactResponse findById(Long id){
        if (Objects.isNull(id)) {
            throw new NullPointerException("Contact id is required");
        }

        Optional<ContactEntity> contact = contactRepository.findById(id);

        if(!contact.isPresent()){
            throw new ResourceNotFoundException(ContactHttpFailureMessages.CONTACT_DOES_NOT_EXIST.getDescription());
        }

        return contactMapper.toResponse(contact.get());
    }

    public boolean existsById(Long id){
        if (Objects.isNull(id)) {
            throw new NullPointerException("Contact id is required");
        }
        return contactRepository.existsById(id);
    }

    public List<ContactResponse> findAllByUserId(Long userId){
        if (Objects.isNull(userId)) {
            throw new NullPointerException("User id is required");
        }
        List<ContactEntity> contacts =  contactRepository.findAllByUser_ExternalUserId(userId);
        return contactMapper.toResponses(contacts);
    }

}
