package org.motechproject.carereporting.web.controller;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("PMD.UnusedPrivateMethod")
public abstract class BaseController {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void initialize() {
        objectMapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
    }

    public String writeAsString(Class<? extends BaseView> viewClass, Object value) {
        String str = null;

        try {
            str = objectMapper.writerWithView(viewClass).writeValueAsString(value);
        } catch (IOException e) {
            throw new CareRuntimeException(e);
        }

        return str;
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    protected Date resolveEndDate(FrequencyEntity frequencyEntity, Date endDate) {
        Date date = DateUtils.addHours(endDate, 2);
        switch(frequencyEntity.getFrequencyName()) {
            case "daily":
                date = DateUtils.addDays(date, 1);
                break;
            case "weekly":
                date = DateUtils.addWeeks(date, 1);
                break;
            case "monthly":
                date = DateUtils.addMonths(date, 1);
                break;
            case "quarterly":
                date = DateUtils.addMonths(date, 3);
                break;
            case "yearly":
                date = DateUtils.addYears(date, 1);
                break;
            default:
                break;
        }
        return date;
    }
}
