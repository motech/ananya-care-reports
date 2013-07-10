package org.motechproject.carereporting.web.chart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class Serie implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Map<BigDecimal, BigDecimal> data;
    private String label;

    public Serie() {
    }

    public Serie(Map<BigDecimal, BigDecimal> data, String label) {
        this.data = data;
        this.label = label;
    }

    public BigDecimal[][] getData() {
        BigDecimal[][] chartData = new BigDecimal[this.data.size()][2];
        int i = 0;
        for (Map.Entry<BigDecimal, BigDecimal> entry: this.data.entrySet()) {
            BigDecimal[] pair = new BigDecimal[2];
            pair[0] = entry.getKey();
            pair[1] = entry.getValue();
            chartData[i] = pair;
            ++i;
        }
        return chartData;
    }

    public void setData(Map<BigDecimal, BigDecimal> data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
