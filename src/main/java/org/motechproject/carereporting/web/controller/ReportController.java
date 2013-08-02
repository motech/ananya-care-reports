package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.dto.ReportDto;
import org.motechproject.carereporting.domain.views.ReportJsonView;
import org.motechproject.carereporting.exception.CareApiRuntimeException;
import org.motechproject.carereporting.exception.EntityException;
import org.motechproject.carereporting.service.ReportService;
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

@RequestMapping(value = "/api/report")
@Controller
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/type", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllReportTypes() {
        return this.writeAsString(ReportJsonView.ReportDetails.class,
                reportService.getAllReportTypes());
    }

    @RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getReportById(@PathVariable Integer reportId) {
        return this.writeAsString(ReportJsonView.ReportDetails.class,
                reportService.getReportById(reportId));
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getAllReports() {
        return this.writeAsString(ReportJsonView.ReportList.class,
                reportService.getAllReports());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void saveReport(@RequestBody @Valid ReportDto reportDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }
        try {
            reportService.createNewReport(
                    reportDto.getIndicatorId(),
                    reportDto.getReportTypeId());
        } catch (EntityException e) {
            bindingResult.rejectValue("name", "duplicate.reportForm.name");
            throw new CareApiRuntimeException(bindingResult.getFieldErrors(), e);
        }
    }

    @RequestMapping(value = "/{reportId}", method = RequestMethod.PUT,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public void updateReport(@PathVariable Integer reportId, @RequestBody @Valid ReportDto reportDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CareApiRuntimeException(bindingResult.getFieldErrors());
        }

        reportService.updateReport(reportDto);
    }

    @RequestMapping(value = "/{reportId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteReport(@PathVariable Integer reportId) {
        reportService.deleteReportById(reportId);
    }

}
