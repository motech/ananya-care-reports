package org.motechproject.carereporting.web.chart.builder;

import org.motechproject.carereporting.web.chart.Serie;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class SerieBuilder extends ParamsBuilder {

    private Serie serie = new Serie();
    private Map<BigDecimal, BigDecimal> data;

    public SerieBuilder() {
        data = new LinkedHashMap<>();
        serie.setData(data);
        serie.setSettings(this.getSettings());
    }

    public SerieBuilder label(String label) {
        serie.setLabel(label);
        return this;
    }

    public SerieBuilder point(BigDecimal x, double y) {
        return point(x, BigDecimal.valueOf(y));
    }

    public SerieBuilder point(double x, double y) {
        return point(BigDecimal.valueOf(x), BigDecimal.valueOf(y));
    }

    public SerieBuilder point(BigDecimal x, Integer y) {
        return point(x, BigDecimal.valueOf(y));
    }

    public SerieBuilder point(Integer x, Integer y) {
        return point(BigDecimal.valueOf(x), BigDecimal.valueOf(y));
    }

    public SerieBuilder point(BigDecimal x, BigDecimal y) {
        data.put(x, y);
        return this;
    }

    public Serie build() {
        return serie;
    }
}
