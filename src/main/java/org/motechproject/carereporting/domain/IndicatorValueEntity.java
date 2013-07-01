package org.motechproject.carereporting.domain;

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

    public IndicatorEntity getIndicator() {
        return indicator;
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

    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    public ComplexConditionEntity getCondition() {
        return condition;
    }

    public void setCondition(ComplexConditionEntity condition) {
        this.condition = condition;
    }
}