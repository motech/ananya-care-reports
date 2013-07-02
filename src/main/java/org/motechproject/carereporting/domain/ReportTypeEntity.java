package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "report_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "report_type_id"))
})
public class ReportTypeEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "chart_type_id")
    private ChartTypeEntity chartType;

    @OneToMany(mappedBy = "reportType", cascade = CascadeType.ALL)
    private Set<ReportEntity> reports;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public ChartTypeEntity getChartType() {
        return chartType;
    }

    public Integer getChartTypeId() {
        return chartType.getId();
    }

    public void setChartType(ChartTypeEntity chartType) {
        this.chartType = chartType;
    }

    @JsonIgnore
    public Set<ReportEntity> getReports() {
        return reports;
    }

    public void setReports(Set<ReportEntity> reports) {
        this.reports = reports;
    }
}
