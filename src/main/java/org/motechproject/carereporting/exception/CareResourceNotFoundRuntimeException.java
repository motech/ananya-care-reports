package org.motechproject.carereporting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashSet;
import java.util.Set;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CareResourceNotFoundRuntimeException extends CareRuntimeException {
    // The error needs to be wrapped in a container, or else RestResponseEntityExceptionHandler
    // will not pass it to the response body
    private Set<String> errorList = new LinkedHashSet<>();

    public Set<String> getErrorList() {
        return errorList;
    }

    private static String constructMessage(Class clazz, Integer id) {
        return "Object of type: " + clazz.toString() + " with id: " + id.toString() + " does not exist.";
    }

    public CareResourceNotFoundRuntimeException(Class clazz, Integer id) {
        super(constructMessage(clazz, id));
        this.errorList.add(this.getMessage());
    }

    public CareResourceNotFoundRuntimeException(Class clazz, Integer id, Throwable cause) {
        super(constructMessage(clazz, id), cause);
        this.errorList.add(this.getMessage());
    }
}
