package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
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
public class IndicatorController {

    @Autowired
    private IndicatorService indicatorService;

    // IndicatorEntity

    @RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<IndicatorEntity> getIndicatorList() {

        return indicatorService.findAllIndicators();
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IndicatorEntity getIndicator(@PathVariable Integer indicatorId) {
        IndicatorEntity indicatorEntity = indicatorService.findIndicatorById(indicatorId);

        if (indicatorEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorEntity.class, indicatorId);
        }

        return indicatorEntity;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewIndicator(@RequestBody @Valid IndicatorEntity indicatorEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        indicatorService.createNewIndicator(indicatorEntity);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateIndicator(@RequestBody @Valid IndicatorEntity indicatorEntity, BindingResult bindingResult,
                                @PathVariable Integer indicatorId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        indicatorEntity.setId(indicatorId);
        indicatorService.updateIndicator(indicatorEntity);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteIndicator(@PathVariable Integer indicatorId) {
        IndicatorEntity indicatorEntity = indicatorService.findIndicatorById(indicatorId);

        if (indicatorEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorEntity.class, indicatorId);
        }

        indicatorService.deleteIndicator(indicatorEntity);
    }

    // IndicatorTypeEntity

    @RequestMapping(value = "/type", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<IndicatorTypeEntity> getIndicatorTypeList() {

        return indicatorService.findAllIndicatorTypes();
    }

    @RequestMapping(value = "/type/{indicatorTypeId}", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IndicatorTypeEntity getIndicatorType(@PathVariable Integer indicatorTypeId) {
        IndicatorTypeEntity indicatorTypeEntity = indicatorService.findIndicatorTypeById(indicatorTypeId);

        if (indicatorTypeEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorTypeEntity.class, indicatorTypeId);
        }

        return indicatorTypeEntity;
    }

    @RequestMapping(value = "/type", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewIndicatorType(@RequestBody @Valid IndicatorTypeEntity indicatorTypeEntity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
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
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        IndicatorTypeEntity foundIndicatorTypeEntity = indicatorService.findIndicatorTypeById(indicatorTypeId);

        if (foundIndicatorTypeEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorTypeEntity.class, indicatorTypeId);
        }

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

        if (indicatorTypeEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorTypeEntity.class, indicatorTypeId);
        }

        indicatorService.deleteIndicatorType(indicatorTypeEntity);
    }

    // IndicatorCategoryEntity

    @RequestMapping(value = "/category", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<IndicatorCategoryEntity> getIndicatorCategoryList() {

        return indicatorService.findAllIndicatorCategories();
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.GET,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IndicatorCategoryEntity getIndicatoryCategory(@PathVariable Integer indicatorCategoryId) {
        IndicatorCategoryEntity indicatorCategoryEntity = indicatorService.findIndicatorCategoryById(indicatorCategoryId);

        if (indicatorCategoryEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class, indicatorCategoryId);
        }

        return indicatorCategoryEntity;
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewIndicatorCategory(@RequestBody @Valid IndicatorCategoryEntity indicatorCategoryEntity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
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
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        IndicatorCategoryEntity foundIndicatorCategoryEntity = indicatorService.findIndicatorCategoryById(indicatorCategoryId);
        if (foundIndicatorCategoryEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class, indicatorCategoryId);
        }

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

        if (indicatorCategoryEntity == null) {
            throw new CareResourceNotFoundRuntimeException(IndicatorCategoryEntity.class, indicatorCategoryId);
        }

        indicatorService.deleteIndicatorCategory(indicatorCategoryEntity);
    }
}
