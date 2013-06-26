package org.motechproject.carereporting.indicator.api;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/indicators")
@Component
@Scope("singleton")
public class IndicatorsApiResource {

    @RequestMapping(method = RequestMethod.GET, consumes = { MediaType.ALL_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    String getIndicatorList() {

        return "OK";
    }
}
