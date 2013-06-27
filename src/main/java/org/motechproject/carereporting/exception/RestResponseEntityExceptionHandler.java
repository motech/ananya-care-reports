package org.motechproject.carereporting.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CareApiRuntimeException.class })
    protected ModelAndView handleApiError(CareApiRuntimeException ex) {
        return new ModelAndView(new MappingJacksonJsonView(), "apiErrors", ex.getErrors());
    }

    @ExceptionHandler(value = { CareResourceNotFoundRuntimeException.class })
    protected ModelAndView handleResourceNotFoundError(CareResourceNotFoundRuntimeException ex) {
        return new ModelAndView(new MappingJacksonJsonView(), "resourceNotFound", ex.getMessage());
    }
}
