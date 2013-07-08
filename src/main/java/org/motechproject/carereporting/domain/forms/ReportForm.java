package org.motechproject.carereporting.domain.forms;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportForm implements Serializable {

    protected static final long serialVersionUID = 0L;

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private Integer indicatorId;

    @NotNull
    private Integer reportTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Integer getReportTypeId() {
        return reportTypeId;
    }

    public void setReportTypeId(Integer reportTypeId) {
        this.reportTypeId = reportTypeId;
    }
}
