package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    private Integer indicatorId;

    @NotNull
    private Integer reportTypeId;

    private Integer areaId;

    private Integer frequencyId;

    private Date startDate;

    private Date endDate;

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

    public Integer getAreaId() {
        return areaId;
    }

    public Integer getFrequencyId() {
        return frequencyId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
