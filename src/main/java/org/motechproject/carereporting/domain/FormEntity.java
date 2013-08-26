package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, IndicatorJsonView.ListFormNames.class })
    private String tableName;

    @NotNull
    @Column (name = "display_name")
    @JsonView({ IndicatorJsonView.IndicatorDetails.class, IndicatorJsonView.ListFormNames.class })
    private String displayName;

    @OneToMany(mappedBy = "form")
    @JsonView(IndicatorJsonView.IndicatorDetails.class)
    private Set<ComputedFieldEntity> computedFields;

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

}
