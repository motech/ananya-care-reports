package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @JsonView({ IndicatorJsonView.IndicatorMainForm.class, IndicatorJsonView.ListFormNames.class })
    private String tableName;

    @NotNull
    @Column (name = "display_name")
    @JsonView({ IndicatorJsonView.IndicatorMainForm.class, IndicatorJsonView.ListFormNames.class })
    private String displayName;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    @JsonView(IndicatorJsonView.IndicatorMainForm.class)
    private Set<ComputedFieldEntity> computedFields;

    @OneToMany(mappedBy = "form", fetch = FetchType.EAGER)
    @JsonView(IndicatorJsonView.IndicatorMainForm.class)
    private Set<FieldEntity> fields;

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
    public Set<ComputedFieldEntity> getComputedFields() {
        return computedFields;
    }

    public void setComputedFields(Set<ComputedFieldEntity> computedFields) {
        this.computedFields = computedFields;
    }

    public Set<FieldEntity> getFields() {
        return fields;
    }

    public void setFields(Set<FieldEntity> fields) {
        this.fields = fields;
    }
}
