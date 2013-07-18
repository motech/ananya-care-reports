package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.views.BaseView;
import org.motechproject.carereporting.enums.FieldType;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "field", uniqueConstraints = @UniqueConstraint(columnNames = { "form_id", "name" }))
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "field_id"))
})
public class FieldEntity extends AbstractEntity {

    private static final int NAME_LENGTH = 100;
    private static final int TYPE_LENGTH = 50;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false, length = NAME_LENGTH)
    @JsonView(BaseView.class)
    private String name;

    @NotNull
    @Column(name = "type", columnDefinition = "character varying", length = TYPE_LENGTH, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @JsonView(BaseView.class)
    private FieldType type;

    @OneToMany(mappedBy = "field1")
    private Set<FieldOperationEntity> fieldOperationsAsField1;

    @OneToMany(mappedBy = "field2")
    private Set<FieldOperationEntity> fieldOperationsAsField2;

    public FieldEntity() {

    }

    @JsonIgnore
    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
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

    @JsonIgnore
    public Set<FieldOperationEntity> getFieldOperationsAsField1() {
        return fieldOperationsAsField1;
    }

    public void setFieldOperationsAsField1(Set<FieldOperationEntity> fieldOperationsAsField1) {
        this.fieldOperationsAsField1 = fieldOperationsAsField1;
    }

    @JsonIgnore
    public Set<FieldOperationEntity> getFieldOperationsAsField2() {
        return fieldOperationsAsField2;
    }

    public void setFieldOperationsAsField2(Set<FieldOperationEntity> fieldOperationsAsField2) {
        this.fieldOperationsAsField2 = fieldOperationsAsField2;
    }
}
