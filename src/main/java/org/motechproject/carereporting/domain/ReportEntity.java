package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.ReportJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "report")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "report_id"))
})
public class ReportEntity extends AbstractEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "report_type_id")
    @JsonView({ BaseView.class })
    private ReportTypeEntity reportType;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    @JsonView({ ReportJsonView.ReportDetails.class })
    private IndicatorEntity indicator;

    @ManyToMany(mappedBy = "reports")
    private Set<DashboardEntity> dashboards;

    @Column(name = "label_x")
    @JsonView({ ReportJsonView.ReportDetails.class })
    private String labelX;

    @Column(name = "label_y")
    @JsonView({ ReportJsonView.ReportDetails.class })
    private String labelY;

    public ReportEntity() {

    }

    public ReportEntity(Integer id) {
        this.id = id;
    }

    public ReportEntity(Integer indicatorId, Integer reportTypeId) {
        this.indicator = new IndicatorEntity(indicatorId);
        this.reportType = new ReportTypeEntity(reportTypeId);

    }

    public ReportEntity(ReportTypeEntity reportType) {
        this.reportType = reportType;
    }

    @JsonIgnore
    public IndicatorEntity getIndicator() {
        return indicator;
    }

    @JsonView({ ReportJsonView.ReportList.class })
    public Integer getIndicatorId() {
        return indicator != null ? indicator.getId() : null;
    }

    @JsonView({ ReportJsonView.ReportList.class })
    public String getIndicatorName() {
        return indicator != null ? indicator.getName() : null;
    }

    public void setIndicator(IndicatorEntity indicator) {
        this.indicator = indicator;
    }

    @JsonIgnore
    public ReportTypeEntity getReportType() {
        return reportType;
    }

    public Integer getReportTypeId() {
        return reportType != null ? reportType.getId() : null;
    }

    public void setReportType(ReportTypeEntity reportType) {
        this.reportType = reportType;
    }

    @JsonIgnore
    public Set<DashboardEntity> getDashboards() {
        return dashboards;
    }

    public void setDashboards(Set<DashboardEntity> dashboards) {
        this.dashboards = dashboards;
    }

    public String getLabelX() {
        return labelX;
    }

    public void setLabelX(String labelX) {
        this.labelX = labelX;
    }

    public String getLabelY() {
        return labelY;
    }

    public void setLabelY(String labelY) {
        this.labelY = labelY;
    }
}
