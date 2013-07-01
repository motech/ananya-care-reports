package org.motechproject.carereporting.web.controller;

import com.google.common.base.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
class ErrorController {

    @RequestMapping("/error")
    public ModelAndView getErrorPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);

        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        StackTraceElement[] stackTrace = null;
        if(throwable != null) {
            stackTrace = throwable.getStackTrace();
        }

        modelAndView.addObject("statusCode", statusCode);
        modelAndView.addObject("requestUri", requestUri);
        modelAndView.addObject("exceptionMessage", exceptionMessage);
        modelAndView.addObject("stackTrace", stackTrace);
        return modelAndView;
    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).getMessage();
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}