package org.motechproject.carereporting.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = { CareApiRuntimeException.class })
    protected ResponseEntity<List<RestFormErrorResponse>> handleApiError(CareApiRuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        List<RestFormErrorResponse> errorsToReturn = getErrorsResponse(ex.getErrors(), request.getLocale());
        return new ResponseEntity<List<RestFormErrorResponse>>(errorsToReturn, httpHeaders, HttpStatus.BAD_REQUEST);
    }

    private List<RestFormErrorResponse> getErrorsResponse(List<FieldError> errors, Locale locale) {
        List<RestFormErrorResponse> responseErrors = new ArrayList<>();
        for (FieldError error: errors) {
            String message = null;
            try {
                message = messageSource.getMessage(error, locale);
            } catch (NoSuchMessageException e) {
                message = error.getDefaultMessage();
            }
            responseErrors.add(new RestFormErrorResponse(error.getField(), message));
        }
        return responseErrors;
    }

    @ExceptionHandler(value = CareSqlRuntimeException.class)
    protected ResponseEntity<Object> handleSqlError(CareSqlRuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { CareResourceNotFoundRuntimeException.class })
    protected ResponseEntity<Object> handleResourceNotFoundError(CareResourceNotFoundRuntimeException ex, WebRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(ex, ex.getErrorList(), httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

    private static class RestFormErrorResponse implements Serializable {
        protected static final long serialVersionUID = 0L;

        private final String field;
        private final String message;

        public RestFormErrorResponse(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
