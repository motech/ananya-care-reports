package org.motechproject.carereporting.domain;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ReportTypeEntity getReportType() {
        return reportType;
    }

    public void setReportType(ReportTypeEntity reportType) {
        this.reportType = reportType;
    }
}
