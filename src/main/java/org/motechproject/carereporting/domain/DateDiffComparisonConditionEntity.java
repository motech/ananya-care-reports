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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "date_diff_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "date_diff_comparison_id"))
})
@JsonTypeName(value = "dateDiff")
public class DateDiffComparisonConditionEntity extends ConditionEntity {

    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id", nullable = false)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComparisonSymbolEntity operator;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "field_2_id")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComputedFieldEntity field2;

    @NotNull
    @Column(name = "value")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private Integer value;

    @Column(name = "offset_1")
    @JsonView({ ComplexConditionJsonView.ListComplexConditions.class })
    private Integer offset1;

    @Column(name = "offset_2")
    @JsonView({ ComplexConditionJsonView.ListComplexConditions.class })
    private Integer offset2;

    public DateDiffComparisonConditionEntity() {
        super();
    }

    public DateDiffComparisonConditionEntity(DateDiffComparisonConditionEntity conditionEntity) {
        super(conditionEntity);

        this.operator = conditionEntity.getOperator();
        this.field2 = conditionEntity.getField2();
        this.value = conditionEntity.getValue();
        this.offset1 = conditionEntity.getOffset1();
        this.offset2 = conditionEntity.getOffset2();
    }

    public ComparisonSymbolEntity getOperator() {
        return operator;
    }

    public void setOperator(ComparisonSymbolEntity operator) {
        this.operator = operator;
    }

    public ComputedFieldEntity getField2() {
        return field2;
    }

    public void setField2(ComputedFieldEntity field2) {
        this.field2 = field2;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getOffset1() {
        return offset1;
    }

    public void setOffset1(Integer offset1) {
        this.offset1 = offset1;
    }

    public Integer getOffset2() {
        return offset2;
    }

    public void setOffset2(Integer offset2) {
        this.offset2 = offset2;
    }
}
