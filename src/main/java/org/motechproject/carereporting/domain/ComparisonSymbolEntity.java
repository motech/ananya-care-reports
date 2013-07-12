package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "comparison_symbol")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "comparison_symbol_id"))
})
public class ComparisonSymbolEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    @JsonView(IndicatorJsonView.IndicatorMainForm.class)
    private String name;

    @OneToMany(mappedBy = "comparisonSymbol", cascade = CascadeType.ALL)
    private Set<ConditionEntity> conditions;

    public ComparisonSymbolEntity() {

    }

    public ComparisonSymbolEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<ConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(Set<ConditionEntity> conditions) {
        this.conditions = conditions;
    }
}
