package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "enum_range_comparison_value")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "enum_range_comparison_value_id"))
})
public class EnumRangeComparisonConditionValueEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "enum_range_comparison_id")
    private EnumRangeComparisonConditionEntity condition;

    @Column(name = "value")
    private String value;

    public EnumRangeComparisonConditionValueEntity() {

    }

    public EnumRangeComparisonConditionValueEntity(String value) {
        this.value = value;
    }

    @JsonIgnore
    public EnumRangeComparisonConditionEntity getCondition() {
        return condition;
    }

    public void setCondition(EnumRangeComparisonConditionEntity condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
