package org.motechproject.carereporting.api;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/indicator")
@Controller
public class IndicatorApiResource {

    @Autowired
    private IndicatorService indicatorService;

    @RequestMapping(method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<IndicatorEntity> getIndicatorList() {
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
    public void createNewIndicator(@Valid IndicatorEntity indicatorEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

        indicatorService.createNewIndicator(indicatorEntity);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateIndicator(@Valid IndicatorEntity indicatorEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getAllErrors());
        }

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
}
