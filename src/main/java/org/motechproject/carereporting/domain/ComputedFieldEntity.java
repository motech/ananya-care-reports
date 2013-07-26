package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "computed_field")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "computed_field_id"))
})
public class ComputedFieldEntity extends AbstractEntity {

    private static final int TYPE_LENGTH = 50;

    @NotNull
    @NotEmpty
    @Column(name = "name")
    @JsonView({ BaseView.class })
    private String name;

    @NotNull
    @Column(name = "type", columnDefinition = "character varying", length = TYPE_LENGTH)
    @Enumerated(value = EnumType.STRING)
    @JsonView({ BaseView.class })
    private FieldType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "form_id")
    @JsonView({ IndicatorJsonView.IndicatorModificationDetails.class })
    private FormEntity form;

    @NotNull
    @OneToMany(mappedBy = "computedField", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<FieldOperationEntity> fieldOperations;

    @OneToMany(mappedBy = "computedField")
    private Set<IndicatorEntity> indicators;

    public ComputedFieldEntity() {

    }

    public ComputedFieldEntity(final String name, final FieldType type, final FormEntity form,
            final Set<FieldOperationEntity> fieldOperations) {
        this.name = name;
        this.type = type;
        this.form = form;
        this.fieldOperations = fieldOperations;

        for (FieldOperationEntity fieldOperationEntity : fieldOperations) {
            if (fieldOperationEntity.getComputedField() == null) {
                fieldOperationEntity.setComputedField(this);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

    public Set<FieldOperationEntity> getFieldOperations() {
        return fieldOperations;
    }

    public void setFieldOperations(Set<FieldOperationEntity> fieldOperations) {
        this.fieldOperations = fieldOperations;
    }

    public Set<IndicatorEntity> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<IndicatorEntity> indicators) {
        this.indicators = indicators;
    }

    @JsonIgnore
    public boolean isRegularField() {
        return  fieldOperations.size() == 1 &&
                fieldOperations.iterator().next().getField2() == null;
    }

    @JsonIgnore
    public FieldEntity getRegularField() {
        if (!isRegularField()) {
            return null;
        }
        return fieldOperations.iterator().next().getField1();
    }
}
