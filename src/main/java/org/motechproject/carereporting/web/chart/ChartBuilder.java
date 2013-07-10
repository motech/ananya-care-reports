package org.motechproject.carereporting.web.chart;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChartBuilder {

    private Chart chart;
    private List<Serie> data = new ArrayList<>();
    private Map<String, Object> settings = new LinkedHashMap<>();

    public ChartBuilder() {
        chart = new Chart(data, settings);
    }

    public ChartBuilder serie(SerieBuilder serieBuilder) {
        return serie(serieBuilder.build());
    }

    public ChartBuilder serie(Serie serie) {
        data.add(serie);
        return this;
    }

    public ChartBuilder param(String key, SettingsBuilder settingsBuilder) {
        return param(key, settingsBuilder.build());
    }

    public ChartBuilder param(String key, Object value) {
        settings.put(key, value);
        return this;
    }

    public Chart build() {
        return chart;
    }
}
