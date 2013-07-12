package org.motechproject.carereporting.web.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.exception.CareRuntimeException;

import javax.annotation.PostConstruct;
import java.io.IOException;

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
}
