package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.TrendJsonView;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TrendIndicatorClassificationDto implements Serializable {

    protected static final long serialVersionUID = 0L;

    @JsonView(TrendJsonView.class)
    private final Set<IndicatorWithTrendDto> indicators = new HashSet<>();

    @JsonView(TrendJsonView.class)
    private final String name;

    public TrendIndicatorClassificationDto(String name) {
        this.name = name;
    }

    public Set<IndicatorWithTrendDto> getIndicators() {
        return indicators;
    }

    public String getName() {
        return name;
    }

}
