package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "indicator_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "indicator_type_id"))
})
public class IndicatorTypeEntity extends AbstractEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    public IndicatorTypeEntity() {

    }

    public IndicatorTypeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
