package org.motechproject.carereporting.web.controller;

import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.views.TrendJsonView;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Date;


@RequestMapping("api/trend")
@Controller
@SuppressWarnings("PMD.UnusedPrivateMethod")
public class TrendController extends BaseController {

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getTrends(@RequestParam Date startDate, @RequestParam Date endDate) {
        UserEntity user = userService.findCurrentlyLoggedUser();
        return writeAsString(TrendJsonView.class,
                indicatorService.getIndicatorsWithTrendsUnderUser(user, startDate, endDate));
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }
}