package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "indicator_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_type_id"))
})
public class IndicatorTypeEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "indicatorType")
    private Set<IndicatorEntity> indicators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
