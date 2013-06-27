package org.motechproject.carereporting.exception;

public class CareResourceNotFoundRuntimeException extends RuntimeException {
    public CareResourceNotFoundRuntimeException(Class clazz, Integer id) {
        super("Object of type: " + clazz.toString() + " with id: " + id.toString() + " does not exist.");
    }
}
