package org.motechproject.carereporting.exception;

public class CareRuntimeException extends RuntimeException {

    public CareRuntimeException() {
        super();
    }

    public CareRuntimeException(String message) {
        super(message);
    }

    public CareRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CareRuntimeException(Throwable cause) {
        super(cause);
    }

    public CareRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
