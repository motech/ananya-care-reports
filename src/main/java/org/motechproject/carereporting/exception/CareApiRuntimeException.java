package org.motechproject.carereporting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CareApiRuntimeException extends RuntimeException {

    private List<FieldError> errors;

    public List<FieldError> getErrors() {
        return errors;
    }

    public CareApiRuntimeException(List<FieldError> errors) {
        this.errors = errors;
    }

    public CareApiRuntimeException(List<FieldError> errors, Exception e) {
        super(e);
        this.errors = errors;
    }
}
