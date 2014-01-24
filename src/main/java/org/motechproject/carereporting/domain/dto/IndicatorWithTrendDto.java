package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.views.TrendJsonView;

import java.io.Serializable;

public class IndicatorWithTrendDto implements Serializable {

    protected static final long serialVersionUID = 0L;

    @JsonView(TrendJsonView.class)
    private IndicatorEntity indicator;

    @JsonView(TrendJsonView.class)
    private float trend;

    public IndicatorWithTrendDto() {
    }

    public IndicatorWithTrendDto(IndicatorEntity indicator, float trend) {
        this.indicator = indicator;
        this.trend = trend;
    }

    public IndicatorEntity getIndicator() {
        return indicator;
    }

    public void setIndicator(IndicatorEntity indicator) {
        this.indicator = indicator;
    }

    public float getTrend() {
        return trend;
    }

    public void setTrend(float trend) {
        this.trend = trend;
    }
}
