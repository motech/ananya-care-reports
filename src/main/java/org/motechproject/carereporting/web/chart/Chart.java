package org.motechproject.carereporting.web.chart;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Chart implements Serializable {

    protected static final long serialVersionUID = 0L;

    private List<Serie> data;
    private Map<String, Object> settings;

    public Chart() {
    }

    public Chart(List<Serie> data, Map<String, Object> settings) {
        this.data = data;
        this.settings = settings;
    }

    public List<Serie> getData() {
        return data;
    }

    public void setData(List<Serie> data) {
        this.data = data;
    }

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }
}
