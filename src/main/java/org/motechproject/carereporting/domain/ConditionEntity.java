package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.ComplexConditionJsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "condition")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "condition_id"))
})
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "field", value = FieldComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "value", value = ValueComparisonConditionEntity.class),
        @JsonSubTypes.Type(name = "date", value = DateDiffComparisonConditionEntity.class)
})
public abstract class ConditionEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "comparison_symbol_id", nullable = false)
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComparisonSymbolEntity comparisonSymbol;

    @ManyToOne
    @JoinColumn(name = "field_1_id")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, ComplexConditionJsonView.ComplexConditionDetails.class })
    private ComputedFieldEntity field1;

    public ComparisonSymbolEntity getComparisonSymbol() {
        return comparisonSymbol;
    }

    public void setComparisonSymbol(ComparisonSymbolEntity comparisonSymbol) {
        this.comparisonSymbol = comparisonSymbol;
    }

    public ComputedFieldEntity getField1() {
        return field1;
    }

    public void setField1(ComputedFieldEntity field1) {
        this.field1 = field1;
    }

}
