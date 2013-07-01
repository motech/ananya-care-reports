package org.motechproject.carereporting.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
    @ManyToOne(targetEntity = IndicatorCategoryEntity.class)
    @JoinColumn(name = "category_id", referencedColumnName = "indicator_category_id", nullable = false)
    private IndicatorCategoryEntity indicatorCategoryEntity;

    @NotNull
    @ManyToOne(targetEntity = LevelEntity.class)
    @JoinColumn(name = "top_level_id", referencedColumnName = "level_id", nullable = false)
    private LevelEntity levelEntity;

    @NotNull
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity userEntity;

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

    public IndicatorCategoryEntity getIndicatorCategoryEntity() {
        return indicatorCategoryEntity;
    }

    public void setIndicatorCategoryEntity(IndicatorCategoryEntity indicatorCategoryEntity) {
        this.indicatorCategoryEntity = indicatorCategoryEntity;
    }

    public LevelEntity getLevelEntity() {
        return levelEntity;
    }

    public void setLevelEntity(LevelEntity levelEntity) {
        this.levelEntity = levelEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
