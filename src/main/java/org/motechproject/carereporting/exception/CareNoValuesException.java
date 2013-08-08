package org.motechproject.carereporting.exception;

public class CareNoValuesException extends CareRuntimeException {

    private static String message = "No values for this indicator";

    public CareNoValuesException() {
        super(message);
    }

    public CareNoValuesException(Throwable cause) {
        super(message, cause);
    }
}
