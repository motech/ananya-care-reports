package org.motechproject.carereporting.web.chart.builder;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParamsBuilder {

    private Map<String, Object> settings = new LinkedHashMap<>();

    public ParamsBuilder param(String key, ParamsBuilder paramsBuilder) {
        return param(key, paramsBuilder.build());
    }

    public ParamsBuilder param(String key, Object value) {
        settings.put(key, value);
        return this;
    }

    public Object build() {
        return settings;
    }
}
