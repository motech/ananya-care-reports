package org.motechproject.carereporting.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CareApiRuntimeException.class })
    protected ResponseEntity<Object> handleApiError(CareApiRuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, ex.getErrors(), httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { CareResourceNotFoundRuntimeException.class })
    protected ResponseEntity<Object> handleResourceNotFoundError(CareResourceNotFoundRuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, ex.getErrorList(), httpHeaders, HttpStatus.BAD_REQUEST, request);
    }
}
