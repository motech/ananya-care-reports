package org.motechproject.carereporting.web.controller;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorTypeEntity;
import org.motechproject.carereporting.domain.dto.DwQueryDto;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.domain.views.QueryJsonView;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.motechproject.carereporting.xml.XmlIndicatorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.UnmarshalException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequestMapping("api/indicator")
@Controller
public class IndicatorController extends BaseController {

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private CronService cronService;

    @Autowired
    private UserService userService;

    @Autowired
    private XmlIndicatorParser xmlIndicatorParser;

    // IndicatorEntity

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getIndicatorList() {
        return this.writeAsString(BaseView.class,
                indicatorService.getAllIndicators());
    }

    @RequestMapping(value = "/creationform", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public String getIndicatorCreationFormDto() {
        return this.writeAsString(IndicatorJsonView.CreationForm.class,
                indicatorService.getIndicatorCreationFormDto());
    }

    @RequestMapping(value = "/query/creationform", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public String getIndicatorQueryCreationFormDto() {
        return this.writeAsString(QueryJsonView.CreationForm.class,
                indicatorService.getIndicatorQueryCreationFormDto());
    }

    @RequestMapping(value = "/queries", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllDwQueries() {
        return this.writeAsString(BaseView.class, indicatorService.getAllTopLevelDwQueries());
    }

    @RequestMapping(value = "/queries/{dwQueryId}", method = RequestMethod.DELETE,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void deleteDwQueryById(@PathVariable Integer dwQueryId) {
        DwQueryEntity dwQueryEntity = indicatorService.getDwQueryById(dwQueryId);
        indicatorService.deleteDwQuery(dwQueryEntity);
    }

    @RequestMapping(value = "/queries/new", method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void createNewDwQuery(@RequestBody @Valid DwQueryDto dwQueryDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        indicatorService.createNewDwQuery(dwQueryDto);
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
            BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }
        IndicatorEntity indicatorEntity = indicatorService.createIndicatorEntityFromDto(indicatorDto);
        if (!canUserCreateIndicator(indicatorEntity, request)) {
            List<FieldError> errors = new ArrayList<>();
            errors.add(new FieldError("indicator", "owners", "You don't have permission to add indicator with this report views."));
            throw new CareApiRuntimeException(errors);
        }
        indicatorService.createNewIndicator(indicatorEntity);
    }

    @RequestMapping(value = "/{indicatorId}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void updateIndicator(@RequestBody @Valid IndicatorDto indicatorDto,
            BindingResult bindingResult, @PathVariable Integer indicatorId) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

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
    @Transactional
    public String getIndicatorCategoryList() {
        return this.writeAsString(IndicatorJsonView.ListIndicatorNames.class,
                indicatorService.getAllIndicatorCategories());
    }

    @RequestMapping(value = "/category/{indicatorCategoryId}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PreAuthorize("hasRole('CAN_EDIT_CATEGORIES')")
    public String getIndicatoryCategory(@PathVariable Integer indicatorCategoryId) {
        return this.writeAsString(IndicatorJsonView.IndicatorDetails.class, indicatorService.getIndicatorCategoryById(indicatorCategoryId));
    }

    @RequestMapping(value = "/category", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CAN_EDIT_CATEGORIES')")
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
    @Transactional(readOnly = false)
    @PreAuthorize("hasRole('CAN_EDIT_CATEGORIES')")
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
    @Transactional(readOnly = false)
    public void deleteIndicatorCategory(@PathVariable Integer indicatorCategoryId) {
        IndicatorCategoryEntity indicatorCategoryEntity = indicatorService.getIndicatorCategoryById(indicatorCategoryId);

        indicatorService.deleteIndicatorCategory(indicatorCategoryEntity);
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
    public String uploadIndicatorXml(@RequestParam("file") MultipartFile[] files, RedirectAttributes redirectAttrs, HttpServletRequest request) {
        try {
            for (MultipartFile file : files) {
                IndicatorEntity indicatorEntity = xmlIndicatorParser.parse(file.getInputStream());
                if (!canUserCreateIndicator(indicatorEntity, request)) {
                    throw new CareRuntimeException("You don't have permission to create indicator with this owner and/or report views.");
                }
                indicatorService.createNewIndicator(indicatorEntity);
            }
            return "redirect:/#/indicators";
        } catch (UnmarshalException e) {
            String message;
            if (e.getLinkedException() != null) {
                if (e.getLinkedException().getCause() != null) {
                    message = e.getLinkedException().getCause().getMessage();
                } else {
                    message = e.getLinkedException().getMessage();
                }
            } else {
                message = e.getMessage();
            }
            redirectAttrs.addFlashAttribute("error", message);
        } catch (Exception e) {
            Logger.getLogger(IndicatorController.class).error("", e);
            redirectAttrs.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/#/indicators/upload-xml";
    }

    private boolean canUserCreateIndicator(IndicatorEntity indicatorEntity, HttpServletRequest request) {
        if (!canUserCreateIndicators(request)) {
            if (!isCurrentUserOwnerOfIndicator(indicatorEntity) || (indicatorEntity.getRoles() != null && indicatorEntity.getRoles().size() > 0)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCurrentUserOwnerOfIndicator(IndicatorEntity indicatorEntity) {
        return indicatorEntity.getOwner().equals(userService.getCurrentlyLoggedUser());
    }

    private boolean canUserCreateIndicators(HttpServletRequest request) {
        return request.isUserInRole("CAN_CREATE_INDICATORS");
    }

    @RequestMapping(value = "{indicatorId}/export/caselistreport", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public ResponseEntity<byte[]> exportCaseListReport(
            @PathVariable Integer indicatorId,
            @RequestParam(required = false) Date fromDate,
            @RequestParam(required = false) Date toDate,
            @RequestParam Integer areaId) {

        IndicatorEntity indicatorEntity = indicatorService.getIndicatorById(indicatorId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd.yyyy_HH.mm");
        String filename = indicatorEntity.getName() + "_" + simpleDateFormat.format(new Date()) + ".csv";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", filename);

        return new ResponseEntity<>(indicatorService.getCaseListReportAsCsv(indicatorEntity, areaId, fromDate, toDate),
                headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/calculator/dateDepth", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getDateDepth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(cronService.getDateDepth());
    }

    @RequestMapping(value = "/calculator/dateDepth", method = RequestMethod.PUT,
        consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void setDateDepth(@RequestBody @Valid Date newDateDepth,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }
        cronService.updateDateDepth(newDateDepth);
    }

    @RequestMapping(value = "calculator/recalculate/{categoryId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void recalculateIndicators(@PathVariable Integer categoryId) {
        indicatorService.calculateAllIndicators(categoryId);
    }
}
