package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.service.ComputedFieldService;
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

@RequestMapping("api/computedfields")
@Controller
public class ComputedFieldController {

    @Autowired
    private ComputedFieldService computedFieldService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<ComputedFieldEntity> getAllComputedFields() {
        return computedFieldService.findAllComputedFields();
    }

    @RequestMapping(value = "/{computedFieldId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ComputedFieldEntity getComputedField(@PathVariable Integer computedFieldId) {
        return computedFieldService.findComputedFieldById(computedFieldId);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewComputedField(@RequestBody @Valid ComputedFieldEntity computedFieldEntity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        computedFieldService.createNewComputedField(computedFieldEntity);
    }
}
