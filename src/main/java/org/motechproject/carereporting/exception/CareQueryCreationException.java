package org.motechproject.carereporting.exception;

public class CareQueryCreationException extends RuntimeException {

    private String header;

    public CareQueryCreationException(String header, String message, Throwable cause) {
        super(message, cause);
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
