package be.fooda.backend.contact.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        this("Resource was not found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}