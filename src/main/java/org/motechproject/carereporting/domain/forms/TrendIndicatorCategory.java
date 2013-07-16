package org.motechproject.carereporting.domain.forms;

import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.TrendJsonView;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TrendIndicatorCategory implements Serializable {

    protected static final long serialVersionUID = 0L;

    @JsonView(TrendJsonView.class)
    private final Set<IndicatorWithTrend> indicators = new HashSet<>();

    @JsonView(TrendJsonView.class)
    private final String name;

    public TrendIndicatorCategory(String name) {
        this.name = name;
    }

    public Set<IndicatorWithTrend> getIndicators() {
        return indicators;
    }

    public String getName() {
        return name;
    }

}
