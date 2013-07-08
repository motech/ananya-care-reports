package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "report")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "report_id"))
})
public class ReportEntity extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportTypeEntity reportType;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private IndicatorEntity indicator;

    public ReportEntity() {

    }

    public ReportEntity(Integer id) {
        this.id = id;
    }

    public ReportEntity(String name, Integer indicatorId, Integer reportTypeId) {
        this.name = name;
        this.indicator = new IndicatorEntity(indicatorId);
        this.reportType = new ReportTypeEntity(reportTypeId);

    }

    public ReportEntity(String name, ReportTypeEntity reportType) {
        this.name = name;
        this.reportType = reportType;
    }

    @JsonIgnore
    public IndicatorEntity getIndicator() {
        return indicator;
    }

    public Integer getIndicatorId() {
        return indicator != null ? indicator.getId() : null;
    }

    public String getIndicatorName() {
        return indicator != null ? indicator.getName() : null;
    }

    public void setIndicator(IndicatorEntity indicator) {
        this.indicator = indicator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportTypeEntity getReportType() {
        return reportType;
    }

    public Integer getReportTypeId() {
        return reportType != null ? reportType.getId() : null;
    }

    public void setReportType(ReportTypeEntity reportType) {
        this.reportType = reportType;
    }
}
