package org.motechproject.carereporting.api;

import org.motechproject.carereporting.service.FormsService;
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
import org.motechproject.carereporting.domain.FormEntity;

import java.util.List;

@Controller
@RequestMapping("/api/forms")
public class FormsApiResource {

    @Autowired
    private FormsService formsService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<FormEntity> getAllForms() {
        return formsService.getAllForms();
    }

    @RequestMapping(value = "/{formId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public FormEntity getForm(@PathVariable Integer formId) {
        return formsService.findFormById(formId);
    }

    @RequestMapping(value = "/{formId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteForm(@PathVariable Integer formId) {
        formsService.deleteFormById(formId);
    }

    @RequestMapping(value = "/tables", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<String> getTables() {
        return formsService.getTables();
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addForm(@RequestBody FormEntity form, BindingResult result) {
        if (!result.hasErrors()) {
            formsService.addForm(form);
        }
    }

    @RequestMapping(value = "/{formId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateForm(@PathVariable Integer formId, @RequestBody FormEntity form, BindingResult result) {
        if (!result.hasErrors()) {
            formsService.updateForm(form);
        }
    }
}
