package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "report")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "report_id"))
})
public class ReportEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "report_type_id")
    private ReportTypeEntity reportType;

    public ReportEntity() {

    }

    public ReportEntity(String name, ReportTypeEntity reportType) {
        this.name = name;
        this.reportType = reportType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public ReportTypeEntity getReportType() {
        return reportType;
    }

    public Integer getReportTypeId() {
        return reportType.getId();
    }

    public void setReportType(ReportTypeEntity reportType) {
        this.reportType = reportType;
    }
}
