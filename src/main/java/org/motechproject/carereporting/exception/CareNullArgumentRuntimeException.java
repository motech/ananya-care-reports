package org.motechproject.carereporting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CareNullArgumentRuntimeException extends CareRuntimeException {

    private static String message = "Argument of this function cannot be null";

    public CareNullArgumentRuntimeException() {
        super(message);
    }

    public CareNullArgumentRuntimeException(Throwable cause) {
        super(message, cause);
    }

}
