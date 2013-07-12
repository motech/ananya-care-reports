package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.ReportJsonView;

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
@Table(name = "report_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "report_type_id"))
})
public class ReportTypeEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    @JsonView(ReportJsonView.ReportDetails.class)
    private String name;

    @OneToMany(mappedBy = "reportType", cascade = CascadeType.ALL)
    private Set<ReportEntity> reports;

    public ReportTypeEntity() {

    }

    public ReportTypeEntity(Integer id) {
        this.id = id;
    }

    public ReportTypeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<ReportEntity> getReports() {
        return reports;
    }

    public void setReports(Set<ReportEntity> reports) {
        this.reports = reports;
    }
}
