package org.motechproject.carereporting.domain.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportFormObject implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    private Integer indicatorId;

    @NotNull
    private Integer reportTypeId;

    private String labelX;

    private String labelY;

    public Integer getId() {
        return id;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public Integer getReportTypeId() {
        return reportTypeId;
    }

    public String getLabelX() {
        return labelX;
    }

    public String getLabelY() {
        return labelY;
    }
}
