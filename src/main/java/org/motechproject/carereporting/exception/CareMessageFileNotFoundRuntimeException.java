package org.motechproject.carereporting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CareMessageFileNotFoundRuntimeException extends RuntimeException {

    public CareMessageFileNotFoundRuntimeException(String fileName) {
        super(String.format("Cannot find file %s.", fileName));
    }

    public CareMessageFileNotFoundRuntimeException(String fileName, Throwable throwable) {
        super(String.format("Cannot find file %s.", fileName), throwable);
    }

}
