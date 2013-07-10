package org.motechproject.carereporting.web.chart;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class SerieBuilder {

    private Serie serie = new Serie();
    private Map<BigDecimal, BigDecimal> data;

    public SerieBuilder() {
        data = new LinkedHashMap<>();
        serie.setData(data);
    }

    public SerieBuilder label(String label) {
        serie.setLabel(label);
        return this;
    }

    public SerieBuilder point(double x, double y) {
        return point(BigDecimal.valueOf(x), BigDecimal.valueOf(y));
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
