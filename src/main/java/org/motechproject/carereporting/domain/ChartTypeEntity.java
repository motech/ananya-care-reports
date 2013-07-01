package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "chart_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "chart_type_id"))
})
public class ChartTypeEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    private String name;

    @OneToMany(mappedBy = "chartType", cascade = CascadeType.ALL)
    private Set<ReportTypeEntity> reportTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ReportTypeEntity> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(Set<ReportTypeEntity> reportTypes) {
        this.reportTypes = reportTypes;
    }
}
