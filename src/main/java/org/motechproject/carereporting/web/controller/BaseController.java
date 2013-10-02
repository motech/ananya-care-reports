package org.motechproject.carereporting.web.controller;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @PostConstruct
    public void initialize() {
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    }

    public String writeAsString(Class<? extends BaseView> viewClass, Object value) {
        String str;

        try {
            str = objectMapper.writerWithView(viewClass).writeValueAsString(value);
        } catch (IOException e) {
            throw new CareRuntimeException(e);
        }

        return str;
    }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

}
