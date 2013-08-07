package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.indicator.IndicatorValueCalculator;
import org.motechproject.carereporting.service.ComplexConditionService;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.xml.XmlIndicatorParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@RequestMapping("api/indicator")
@Controller
public class IndicatorController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(IndicatorController.class);

    private static final String UNDEFINED = "undefined";

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private IndicatorValueCalculator indicatorValueCalculator;

    @Autowired
    private CronService cronService;

    @Autowired
    private XmlIndicatorParser xmlIndicatorParser;

    @Autowired
    private ComplexConditionService complexConditionService;

    // IndicatorEntity

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatorList() {
        return this.writeAsString(BaseView.class,
                indicatorService.getAllIndicators());
    }

    @RequestMapping(value = "/filter/{categoryId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatorListByCategoryId(@PathVariable Integer categoryId) {
        return this.writeAsString(BaseView.class,
                indicatorService.getIndicatorsByCategoryId(categoryId));
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicator(@PathVariable Integer indicatorId) {
        return this.writeAsString(IndicatorJsonView.IndicatorModificationDetails.class,
                indicatorService.getIndicatorById(indicatorId));
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void createNewIndicator(@RequestBody @Valid IndicatorDto indicatorDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorService.createNewIndicatorFromDto(indicatorDto);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void updateIndicator(@RequestBody @Valid IndicatorDto indicatorDto,
            BindingResult bindingResult, @PathVariable Integer indicatorId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorDto.setId(indicatorId);
        indicatorService.updateIndicatorFromDto(indicatorDto);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteIndicator(@PathVariable Integer indicatorId) {
        IndicatorEntity indicatorEntity = indicatorService.getIndicatorById(indicatorId);
        indicatorService.deleteIndicator(indicatorEntity);
    }

    // IndicatorTypeEntity

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Set<IndicatorTypeEntity> getIndicatorTypeList() {
        return indicatorService.getAllIndicatorTypes();
    }

    @RequestMapping(value = "/type/{indicatorTypeId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IndicatorTypeEntity getIndicatorType(@PathVariable Integer indicatorTypeId) {
        return indicatorService.getIndicatorTypeById(indicatorTypeId);
    }
    // IndicatorCategoryEntity

    @RequestMapping(value = "/category", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatorCategoryList() {

        return this.writeAsString(IndicatorJsonView.IndicatorDetails.class,
                indicatorService.getAllIndicatorCategories());
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatoryCategory(@PathVariable Integer indicatorCategoryId) {
        return this.writeAsString(IndicatorJsonView.IndicatorDetails.class, indicatorService.getIndicatorCategoryById(indicatorCategoryId));
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void createNewIndicatorCategory(@RequestBody @Valid IndicatorCategoryEntity indicatorCategoryEntity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorService.createNewIndicatorCategory(indicatorCategoryEntity);
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void updateIndicatorCategory(@RequestBody @Valid IndicatorCategoryEntity indicatorCategoryEntity,
            BindingResult bindingResult, @PathVariable Integer indicatorCategoryId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        IndicatorCategoryEntity foundIndicatorCategoryEntity = indicatorService.getIndicatorCategoryById(indicatorCategoryId);

        foundIndicatorCategoryEntity.setName(indicatorCategoryEntity.getName());
        foundIndicatorCategoryEntity.setShortCode(indicatorCategoryEntity.getShortCode());
        indicatorService.updateIndicatorCategory(foundIndicatorCategoryEntity);
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteIndicatorCategory(@PathVariable Integer indicatorCategoryId) {
        IndicatorCategoryEntity indicatorCategoryEntity = indicatorService.getIndicatorCategoryById(indicatorCategoryId);

        indicatorService.deleteIndicatorCategory(indicatorCategoryEntity);
    }

    @RequestMapping(value = "/calculator/recalculate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void recalculateAllIndicatorValues() {
        FrequencyEntity frequencyEntity = cronService.getFrequencyByName(UNDEFINED);
        indicatorValueCalculator.calculateIndicatorValues(frequencyEntity);
    }

    @RequestMapping(value = "/calculator/frequencies", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllFrequencies() {
        return writeAsString(BaseView.class, cronService.getAllFrequencies());
    }

    @RequestMapping(value = "/calculator/frequency/daily", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDailyTaskTime() {
        return cronService.getDailyCronTask().getTime();
    }

    @RequestMapping(value = "/calculator/frequency/daily", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void updateDailyTaskTime(@RequestBody String time) {
        CronTaskEntity cronTaskEntity = cronService.getDailyCronTask();
        cronTaskEntity.setTime(time);

        cronService.updateCronTask(cronTaskEntity);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public String uploadIndicatorXml(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            IndicatorEntity indicatorEntity = xmlIndicatorParser.parse(file.getInputStream());
            complexConditionService.createNewComplexCondition(indicatorEntity.getComplexCondition());
            indicatorService.createNewIndicator(indicatorEntity);
            return "redirect:/#/indicators/new";
        } catch (Exception e) {
            LOG.warn("", e);
            throw e;
        }
    }

}
