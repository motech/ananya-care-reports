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
@Table(name = "form")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "form_id"))
})
public class FormEntity extends AbstractEntity {

    @NotNull
    @Column(name = "table_name")
    private String tableName;

    @NotNull
    @Column (name = "display_name")
    private String displayName;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private Set<ComplexConditionEntity> complexConditions;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonIgnore
    public Set<ComplexConditionEntity> getComplexConditions() {
        return complexConditions;
    }

    public void setComplexConditions(Set<ComplexConditionEntity> complexConditions) {
        this.complexConditions = complexConditions;
    }
}
