package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
import org.motechproject.carereporting.domain.views.TrendJsonView;
import org.springframework.security.core.context.SecurityContextHolder;

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
import java.util.LinkedHashSet;
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

    @JsonProperty("indicators")
    @JsonView({ TrendJsonView.class, IndicatorJsonView.ListIndicatorNames.class })
    public Set<IndicatorEntity> getIndicators() {
        if (SecurityContextHolder.getContext() == null
                || SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userEntity == null) {
            return null;
        }

        for (RoleEntity roleEntity : userEntity.getRoles()) {
            if ("Admin".equals(roleEntity.getName())) {
                return this.indicators;
            }
        }

        Set<IndicatorEntity> indicatorEntitySet = new LinkedHashSet<>();
        for (IndicatorEntity indicatorEntity : this.indicators) {
            if (userEntity.getId().equals(indicatorEntity.getOwner().getId())) {
                indicatorEntitySet.add(indicatorEntity);
            }
        }

        return indicatorEntitySet;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
