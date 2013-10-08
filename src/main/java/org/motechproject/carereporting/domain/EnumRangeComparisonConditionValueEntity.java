package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.BaseView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "enum_range_comparison_value")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "enum_range_comparison_value_id"))
})
public class EnumRangeComparisonConditionValueEntity extends AbstractEntity implements Cloneable {

    @Column(name = "value")
    @JsonView({ BaseView.class })
    private String value;

    public EnumRangeComparisonConditionValueEntity() {

    }

    public EnumRangeComparisonConditionValueEntity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        EnumRangeComparisonConditionValueEntity valueEntity = new EnumRangeComparisonConditionValueEntity();

        valueEntity.setValue(this.getValue());

        return valueEntity;
    }
}
