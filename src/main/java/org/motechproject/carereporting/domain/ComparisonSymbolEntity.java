package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

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
    private String name;

    @OneToMany(mappedBy = "comparisonSymbol", cascade = CascadeType.ALL)
    private Set<ComplexConditionEntity> complexConditions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<ComplexConditionEntity> getComplexConditions() {
        return complexConditions;
    }

    public void setComplexConditions(Set<ComplexConditionEntity> complexConditions) {
        this.complexConditions = complexConditions;
    }
}
