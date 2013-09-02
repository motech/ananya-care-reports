package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.dto.ComputedFieldDto;
import org.motechproject.carereporting.domain.views.ComputedFieldView;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequestMapping("api/computedfields")
@Controller
public class ComputedFieldController extends BaseController {

    @Autowired
    private ComputedFieldService computedFieldService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public String getAllComputedFields() {
        return writeAsString(ComputedFieldView.class, computedFieldService.getAllComputedFields());
    }

    @RequestMapping(value = "/{computedFieldId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public String getComputedField(@PathVariable Integer computedFieldId) {
        return writeAsString(ComputedFieldView.class, computedFieldService.getComputedFieldById(computedFieldId));
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void createNewComputedField(@RequestBody @Valid ComputedFieldDto computedFieldDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        computedFieldService.createNewComputedFieldFromDto(computedFieldDto);
    }

    @RequestMapping(value = "/{computedFieldId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void updateComputedField(@RequestBody @Valid ComputedFieldDto computedFieldDto,
                                    @PathVariable Integer computedFieldId,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }
        computedFieldService.updateComputedFieldFromDto(computedFieldId, computedFieldDto);
    }

    @RequestMapping(value = "/{computedFieldId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteComputedField(@PathVariable Integer computedFieldId) {
        computedFieldService.deleteComputedField(computedFieldId);
    }
}
