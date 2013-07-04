package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

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
public class AreaEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "level_id")
    private LevelEntity level;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private Set<IndicatorValueEntity> indicatorValues;

    public AreaEntity(String name, LevelEntity level) {
        this.name = name;
        this.level = level;
    }

    public AreaEntity() {

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

    @JsonIgnore
    public Set<IndicatorValueEntity> getIndicatorValues() {
        return indicatorValues;
    }

    public void setIndicatorValues(Set<IndicatorValueEntity> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }
}
