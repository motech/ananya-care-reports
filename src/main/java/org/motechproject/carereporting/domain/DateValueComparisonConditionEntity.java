package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "date_value_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "date_value_comparison_id"))
})
@JsonTypeName(value = "dateValue")
public class DateValueComparisonConditionEntity extends ConditionEntity {

    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id", nullable = false)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComparisonSymbolEntity operator;

    @NotNull
    @Column(name = "value")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    @Temporal(TemporalType.DATE)
    private Date value;

    @Column(name = "offset_1")
    @JsonView({ ComplexConditionJsonView.ListComplexConditions.class })
    private Integer offset1;

    public DateValueComparisonConditionEntity() {
        super();
    }

    public DateValueComparisonConditionEntity(DateValueComparisonConditionEntity conditionEntity) {
        super(conditionEntity);

        this.operator = conditionEntity.getOperator();
        this.value = conditionEntity.getValue();
        this.offset1 = conditionEntity.getOffset1();
    }

    public ComparisonSymbolEntity getOperator() {
        return operator;
    }

    public void setOperator(ComparisonSymbolEntity operator) {
        this.operator = operator;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    public Integer getOffset1() {
        return offset1;
    }

    public void setOffset1(Integer offset1) {
        this.offset1 = offset1;
    }
}
