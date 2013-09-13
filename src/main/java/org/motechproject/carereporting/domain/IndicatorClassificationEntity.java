package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.domain.views.TrendJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "indicator_classification")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_classification_id"))
})
public class IndicatorClassificationEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    @JsonView({ BaseView.class })
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id")
    private DashboardEntity dashboard;

    @ManyToMany(mappedBy = "classifications", fetch = FetchType.LAZY)
    @JsonView({ TrendJsonView.class, IndicatorJsonView.ListIndicatorNames.class })
    private Set<IndicatorEntity> indicators;

    public IndicatorClassificationEntity() {

    }

    public IndicatorClassificationEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public DashboardEntity getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardEntity dashboard) {
        this.dashboard = dashboard;
    }

    @JsonIgnore
    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
