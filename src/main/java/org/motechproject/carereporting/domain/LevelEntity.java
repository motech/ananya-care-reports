package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.DashboardJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

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
@Table(name = "level")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "level_id"))
})
public class LevelEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    @JsonView({ IndicatorJsonView.CreationForm.class })
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_level_id")
    private LevelEntity parentLevel;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Set<AreaEntity> areas;

    @Column(name = "hierarchy_depth")
    @JsonView({ DashboardJsonView.class, IndicatorJsonView.CreationForm.class })
    private Integer hierarchyDepth;

    public LevelEntity() {

    }

    public LevelEntity(String name, LevelEntity parentLevel) {
        this.name = name;
        this.parentLevel = parentLevel;
    }

    public Integer getHierarchyDepth() {
        return hierarchyDepth;
    }

    public void setHierarchyDepth(Integer hierarchyDepth) {
        this.hierarchyDepth = hierarchyDepth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public LevelEntity getParentLevel() {
        return parentLevel;
    }

    public Integer getParentLevelId() {
        return (parentLevel == null) ? null : parentLevel.getId();
    }

    public void setParentLevel(LevelEntity parentLevel) {
        this.parentLevel = parentLevel;
    }

    @JsonIgnore
    public Set<AreaEntity> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaEntity> areas) {
        this.areas = areas;
    }
}
