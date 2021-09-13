package be.fooda.backend.contact.view.controller;

import be.fooda.backend.contact.model.dto.ContactResponse;
import be.fooda.backend.contact.model.dto.CreateContactRequest;
import be.fooda.backend.contact.model.dto.UpdateContactRequest;
import be.fooda.backend.contact.service.exception.ResourceAlreadyExistsException;
import be.fooda.backend.contact.service.exception.ResourceNotFoundException;
import be.fooda.backend.contact.service.flow.ContactFlow;
import be.fooda.backend.contact.model.http.ContactHttpFailureMessages;
import be.fooda.backend.contact.model.http.ContactHttpSuccessMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactFlow contactFlow;

    private static final String GET_ALL_CONTACTS  = "/get/all";
    private static final String CONTACT_EXISTS_BY_ID  = "/exists";
    private static final String GET_BY_CONTACT_ID  = "/get/id";
    private static final String GET_ALL_CONTACTS_BY_USER_ID  = "/get/userId";
    private static final String ADD_CONTACT  = "/create";
    private static final String UPDATE_CONTACT  = "/update";
    private static final String DELETE_CONTACT_BY_ID = "/delete";

    @GetMapping(GET_ALL_CONTACTS)
    public ResponseEntity findAllContacts() {

        List<ContactResponse> contacts = contactFlow.findAll();

        if (contacts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ContactHttpFailureMessages.NO_CONTACTS_FOUND.getDescription());
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(contacts);
    }

    @GetMapping(GET_BY_CONTACT_ID)
    public ResponseEntity findContactById(@RequestParam Long id) {
        try{
            ContactResponse contact = contactFlow.findById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(contact);
        } catch (NullPointerException | ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(GET_ALL_CONTACTS_BY_USER_ID)
    public ResponseEntity findContactByUserId(@RequestParam Long userId) {
        List<ContactResponse> foundContact = contactFlow.findAllByUserId(userId);
        return foundContact.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(ContactHttpFailureMessages.CONTACT_DOES_NOT_EXIST)
                : ResponseEntity.status(HttpStatus.FOUND).body(foundContact);
    }

    @PostMapping(ADD_CONTACT)
    public ResponseEntity createContact(@RequestBody @Valid CreateContactRequest request) {
        try{
            contactFlow.createContact(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ContactHttpSuccessMessages.CONTACT_CREATED.getDescription());
        } catch (NullPointerException | ResourceAlreadyExistsException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(UPDATE_CONTACT)
    public ResponseEntity updateContact(@RequestParam("id") Long id, @RequestBody UpdateContactRequest request){
        try{
            contactFlow.updateContact(id, request);

            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(ContactHttpSuccessMessages.CONTACT_UPDATED.getDescription());
        } catch (NullPointerException | ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(DELETE_CONTACT_BY_ID)
    public ResponseEntity deleteContactById(@RequestParam("id") Long id){
        try{
            contactFlow.deleteContactById(id);

            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(ContactHttpSuccessMessages.CONTACT_DELETED.getDescription());
        } catch (NullPointerException | ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(CONTACT_EXISTS_BY_ID)
    public ResponseEntity contactExistsById(@RequestParam Long id) {
        try{
            boolean contactExists = contactFlow.existsById(id);
            return contactExists
                    ? ResponseEntity.status(HttpStatus.FOUND).body(ContactHttpSuccessMessages.CONTACT_EXISTS.getDescription())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ContactHttpFailureMessages.CONTACT_DOES_NOT_EXIST.getDescription());
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
