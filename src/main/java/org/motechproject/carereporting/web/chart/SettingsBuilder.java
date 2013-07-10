package org.motechproject.carereporting.web.chart;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsBuilder {

    private Map<String, Object> settings = new LinkedHashMap<>();

    public SettingsBuilder param(String key, SettingsBuilder settingsBuilder) {
        return param(key, settingsBuilder.build());
    }

    public SettingsBuilder param(String key, Object value) {
        settings.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return settings;
    }
}
