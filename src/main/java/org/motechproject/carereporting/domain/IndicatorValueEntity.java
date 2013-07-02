package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "indicator_value")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_value_id"))
})
public class IndicatorValueEntity extends AbstractEntity {

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private IndicatorEntity indicator;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "area_id")
    private AreaEntity area;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "condition_id")
    private ComplexConditionEntity condition;

    @JsonIgnore
    public IndicatorEntity getIndicator() {
        return indicator;
    }

    public Integer getIndicatorId() {
        return indicator.getId();
    }

    public void setIndicator(IndicatorEntity indicator) {
        this.indicator = indicator;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonIgnore
    public AreaEntity getArea() {
        return area;
    }

    public Integer getAreaId() {
        return area.getId();
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    @JsonIgnore
    public ComplexConditionEntity getCondition() {
        return condition;
    }

    public Integer getConditionId() {
        return condition.getId();
    }

    public void setCondition(ComplexConditionEntity condition) {
        this.condition = condition;
    }
}
