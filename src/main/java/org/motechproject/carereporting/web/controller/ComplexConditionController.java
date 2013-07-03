package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.forms.ComplexConditionFormObject;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Set;

@RequestMapping("api/complexcondition")
@Controller
public class ComplexConditionController {

    @Autowired
    private ComplexConditionService complexConditionService;

    @RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<ComplexConditionEntity> getComplexConditionList() {
        return complexConditionService.findAllComplexConditions();
    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ComplexConditionEntity getComplexCondition(@PathVariable Integer complexConditionId) {
        return complexConditionService.findComplexConditionById(complexConditionId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewComplexCondition(@RequestBody @Valid ComplexConditionFormObject complexConditionFormObject,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        complexConditionService.createNewComplexCondition(complexConditionFormObject);
    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateComplexCondition(@RequestBody @Valid ComplexConditionFormObject complexConditionFormObject,
            BindingResult bindingResult, @PathVariable Integer complexConditionId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        complexConditionFormObject.setId(complexConditionId);
        complexConditionService.updateComplexCondition(complexConditionFormObject);
    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteComplexCondition(@PathVariable Integer complexConditionId) {
        ComplexConditionEntity complexConditionEntity = complexConditionService.findComplexConditionById(complexConditionId);
        complexConditionService.deleteComplexCondition(complexConditionEntity);
    }
}
