package org.motechproject.carereporting.domain;

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
@Table(name = "level")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "level_id"))
})
public class LevelEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_level_id")
    private LevelEntity parentLevel;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private Set<AreaEntity> areas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LevelEntity getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(LevelEntity parentLevel) {
        this.parentLevel = parentLevel;
    }

    public Set<AreaEntity> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaEntity> areas) {
        this.areas = areas;
    }
}