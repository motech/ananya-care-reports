package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.dto.ComplexConditionFormObject;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
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

@RequestMapping("api/complexcondition")
@Controller
public class ComplexConditionController extends BaseController {

    @Autowired
    private ComplexConditionService complexConditionService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getComplexConditionList() {
        return this.writeAsString(ComplexConditionJsonView.ListComplexConditions.class,
            complexConditionService.getAllComplexConditions());
    }

    @RequestMapping(value = "/operatortype", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getOperatorTypeList() {
        return this.writeAsString(ComplexConditionJsonView.ListOperatorTypes.class,
            complexConditionService.getAllOperatorTypes());
    }

    @RequestMapping(value = "/comparisonsymbol", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getComparisonSymbolList() {
        return this.writeAsString(ComplexConditionJsonView.ListComparisonSymbols.class,
            complexConditionService.getAllComparisonSymbols());
    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getComplexCondition(@PathVariable Integer complexConditionId) {
        return this.writeAsString(ComplexConditionJsonView.ComplexConditionDetails.class,
            complexConditionService.getComplexConditionById(complexConditionId));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewComplexCondition(@RequestBody @Valid ComplexConditionFormObject complexConditionFormObject,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
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
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        complexConditionFormObject.setId(complexConditionId);
        complexConditionService.updateComplexCondition(complexConditionFormObject);
    }

    @RequestMapping(value = "/{complexConditionId}", method = RequestMethod.DELETE,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteComplexCondition(@PathVariable Integer complexConditionId) {
        ComplexConditionEntity complexConditionEntity = complexConditionService.getComplexConditionById(complexConditionId);
        complexConditionService.deleteComplexCondition(complexConditionEntity);
    }
}
