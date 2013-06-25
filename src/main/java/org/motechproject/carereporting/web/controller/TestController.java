package org.motechproject.carereporting.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/test")
public class TestController {

    public static final String HAS_ROLE_TEST = "hasRole('testRole')";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PreAuthorize(HAS_ROLE_TEST)
    @ResponseStatus(HttpStatus.OK)
    public void doTest() {
    }

}
