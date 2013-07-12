package org.motechproject.carereporting.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.validator.constraints.NotEmpty;
import org.motechproject.carereporting.domain.views.IndicatorJsonView;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "field", uniqueConstraints = @UniqueConstraint(columnNames = { "form_id", "name" }))
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "field_id"))
})
public class FieldEntity extends AbstractEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id", nullable = false)
    private FormEntity form;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false, length = 100)
    @JsonView(IndicatorJsonView.IndicatorDetails.class)
    private String name;

    @NotNull
    @Column(name = "type", columnDefinition = "character varying", length = 50, nullable = false)
    @Enumerated(value = EnumType.STRING)
    @JsonView(IndicatorJsonView.IndicatorDetails.class)
    private FieldType type;

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

}
