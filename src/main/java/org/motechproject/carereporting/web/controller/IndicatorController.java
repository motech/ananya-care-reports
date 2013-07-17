package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.forms.IndicatorFormObject;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.service.IndicatorService;
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

@RequestMapping("api/indicator")
@Controller
public class IndicatorController extends BaseController {

    @Autowired
    private IndicatorService indicatorService;

    // IndicatorEntity

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatorList() {
        return this.writeAsString(BaseView.class,
                indicatorService.findAllIndicators());
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicator(@PathVariable Integer indicatorId) {
        return this.writeAsString(IndicatorJsonView.IndicatorModificationDetails.class,
                indicatorService.findIndicatorById(indicatorId));
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewIndicator(@RequestBody @Valid IndicatorFormObject indicatorFormObject,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorService.createNewIndicatorFromFormObject(indicatorFormObject);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateIndicator(@RequestBody @Valid IndicatorFormObject indicatorFormObject,
            BindingResult bindingResult, @PathVariable Integer indicatorId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorFormObject.setId(indicatorId);
        indicatorService.updateIndicatorFromFormObject(indicatorFormObject);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.DELETE,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteIndicator(@PathVariable Integer indicatorId) {
        IndicatorEntity indicatorEntity = indicatorService.findIndicatorById(indicatorId);
        indicatorService.deleteIndicator(indicatorEntity);
    }

    // IndicatorTypeEntity

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<IndicatorTypeEntity> getIndicatorTypeList() {
        return indicatorService.findAllIndicatorTypes();
    }

    @RequestMapping(value = "/type/{indicatorTypeId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IndicatorTypeEntity getIndicatorType(@PathVariable Integer indicatorTypeId) {
        return indicatorService.findIndicatorTypeById(indicatorTypeId);
    }

    @RequestMapping(value = "/type", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewIndicatorType(@RequestBody @Valid IndicatorTypeEntity indicatorTypeEntity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorService.createNewIndicatorType(indicatorTypeEntity);
    }

    @RequestMapping(value = "/type/{indicatorTypeId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateIndicatorType(@RequestBody @Valid IndicatorTypeEntity indicatorTypeEntity,
            BindingResult bindingResult, @PathVariable Integer indicatorTypeId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        IndicatorTypeEntity foundIndicatorTypeEntity = indicatorService.findIndicatorTypeById(indicatorTypeId);

        foundIndicatorTypeEntity.setName(indicatorTypeEntity.getName());
        indicatorService.updateIndicatorType(foundIndicatorTypeEntity);
    }

    @RequestMapping(value = "/type/{indicatorTypeId}", method = RequestMethod.DELETE,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteIndicatorType(@PathVariable Integer indicatorTypeId) {
        IndicatorTypeEntity indicatorTypeEntity = indicatorService.findIndicatorTypeById(indicatorTypeId);

        indicatorService.deleteIndicatorType(indicatorTypeEntity);
    }

    // IndicatorCategoryEntity

    @RequestMapping(value = "/category", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatorCategoryList() {

        return this.writeAsString(IndicatorJsonView.IndicatorDetails.class,
                indicatorService.findAllIndicatorCategories());
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IndicatorCategoryEntity getIndicatoryCategory(@PathVariable Integer indicatorCategoryId) {
        return indicatorService.findIndicatorCategoryById(indicatorCategoryId);
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewIndicatorCategory(@RequestBody @Valid IndicatorCategoryEntity indicatorCategoryEntity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorService.createNewIndicatorCategory(indicatorCategoryEntity);
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateIndicatorCategory(@RequestBody @Valid IndicatorCategoryEntity indicatorCategoryEntity,
            BindingResult bindingResult, @PathVariable Integer indicatorCategoryId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        IndicatorCategoryEntity foundIndicatorCategoryEntity = indicatorService.findIndicatorCategoryById(indicatorCategoryId);

        foundIndicatorCategoryEntity.setName(indicatorCategoryEntity.getName());
        indicatorService.updateIndicatorCategory(foundIndicatorCategoryEntity);
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.DELETE,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteIndicatorCategory(@PathVariable Integer indicatorCategoryId) {
        IndicatorCategoryEntity indicatorCategoryEntity = indicatorService.findIndicatorCategoryById(indicatorCategoryId);

        indicatorService.deleteIndicatorCategory(indicatorCategoryEntity);
    }
}
