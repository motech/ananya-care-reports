package org.motechproject.carereporting.domain.dto;

import java.io.Serializable;
import java.util.List;

public class CategorizedValueDto implements Serializable {
    protected static final long serialVersionUID = 0L;

    private String categoryName;
    private List<IndicatorValueDto> values;

    public CategorizedValueDto(String categoryName, List<IndicatorValueDto> values) {
        this.categoryName = categoryName;
        this.values = values;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<IndicatorValueDto> getValues() {
        return values;
    }
}
