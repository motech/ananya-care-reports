package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.DashboardJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "area")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "area_id"))
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    @JsonView({ BaseView.class })
    private String name;

    @NotNull
    @ManyToOne(targetEntity = LevelEntity.class)
    @JoinColumn(name = "level_id")
    @JsonView({ IndicatorJsonView.IndicatorModificationDetails.class, DashboardJsonView.class })
    private LevelEntity level;

    @ManyToOne
    @JoinColumn(name = "parent_area_id")
    @JsonView({ IndicatorJsonView.IndicatorModificationDetails.class, DashboardJsonView.class })
    private AreaEntity parentArea;

    @OneToMany(mappedBy = "parentArea")
    private Set<AreaEntity> childAreas;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private Set<IndicatorValueEntity> indicatorValues;

    public AreaEntity(String name, LevelEntity level) {
        this.name = name;
        this.level = level;
    }

    public AreaEntity() {

    }

    @JsonIgnore
    public AreaEntity getParentArea() {
        return parentArea;
    }

    public Integer getParentAreaId() {
        return parentArea != null ? parentArea.getId() : null;
    }

    public void setParentArea(AreaEntity parentArea) {
        this.parentArea = parentArea;
    }

    @JsonIgnore
    public Set<AreaEntity> getChildAreas() {
        return childAreas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public LevelEntity getLevel() {
        return level;
    }

    public Integer getLevelId() {
        return level.getId();
    }

    public void setLevel(LevelEntity level) {
        this.level = level;
    }

    public Integer getLevelHierarchyDepth() {
        return level != null ? level.getHierarchyDepth() : null;
    }

    @JsonIgnore
    public Set<IndicatorValueEntity> getIndicatorValues() {
        return indicatorValues;
    }

    public void setIndicatorValues(Set<IndicatorValueEntity> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }

    @Override
    public String toString() {
        return name;
    }
}
