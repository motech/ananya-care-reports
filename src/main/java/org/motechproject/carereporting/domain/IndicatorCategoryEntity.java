package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "indicator_category")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_category_id"))
})
public class IndicatorCategoryEntity extends AbstractEntity {

    @NotNull
    @Column (name = "name")
    private String name;

    @NotNull
    @ManyToMany(mappedBy = "categories")
    private Set<IndicatorEntity> indicators;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }
}
