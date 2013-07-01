package org.motechproject.carereporting.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "indicator")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_id"))
})
public class IndicatorEntity extends AbstractEntity {

    @NotNull
    @ManyToOne(targetEntity = IndicatorTypeEntity.class)
    @JoinColumn(name = "type_id", referencedColumnName = "indicator_type_id", nullable = false)
    private IndicatorTypeEntity indicatorTypeEntity;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "indicator_indicator_category", joinColumns = { @JoinColumn(name = "indicator_id") },
            inverseJoinColumns = { @JoinColumn(name = "indicator_category_id") })
    private Set<IndicatorCategoryEntity> indicatorCategoryEntities;

    @NotNull
    @ManyToOne(targetEntity = LevelEntity.class)
    @JoinColumn(name = "top_level_id", referencedColumnName = "level_id", nullable = false)
    private LevelEntity levelEntity;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "indicator_user", joinColumns = { @JoinColumn(name = "indicator_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<UserEntity> userEntities;

    @NotNull
    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @NotNull
    @NotEmpty
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public IndicatorTypeEntity getIndicatorTypeEntity() {
        return indicatorTypeEntity;
    }

    public void setIndicatorTypeEntity(IndicatorTypeEntity indicatorTypeEntity) {
        this.indicatorTypeEntity = indicatorTypeEntity;
    }

    public Set<IndicatorCategoryEntity> getIndicatorCategoryEntities() {
        return indicatorCategoryEntities;
    }

    public void setIndicatorCategoryEntities(Set<IndicatorCategoryEntity> indicatorCategoryEntities) {
        this.indicatorCategoryEntities = indicatorCategoryEntities;
    }

    public LevelEntity getLevelEntity() {
        return levelEntity;
    }

    public void setLevelEntity(LevelEntity levelEntity) {
        this.levelEntity = levelEntity;
    }

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
