package org.motechproject.carereporting.exception;

public class CareSqlRuntimeException extends CareRuntimeException {

    public CareSqlRuntimeException(String message) {
        super(message);
    }

    public CareSqlRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CareSqlRuntimeException(Throwable cause) {
        super(cause);
    }

    public CareSqlRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
