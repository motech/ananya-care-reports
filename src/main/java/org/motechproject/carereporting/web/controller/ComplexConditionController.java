package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/complexcondition")
@Controller
public class ComplexConditionController {

    @RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<ComplexConditionEntity> getComplexConditionList() {

        return null;
    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ComplexConditionEntity getComplexCondition(@PathVariable Integer complexConditionId) {

        return null;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewComplexCondition(@RequestBody @Valid ComplexConditionEntity complexConditionEntity,
            BindingResult bindingResult) {

    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateComplexCondition(@RequestBody @Valid ComplexConditionEntity complexConditionEntity,
            BindingResult bindingResult, @PathVariable Integer complexConditionId) {

    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteComplexCondition(@PathVariable Integer complexConditionId) {

    }
}
