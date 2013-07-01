package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "operator_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "operator_type_id"))
})
public class OperatorTypeEntity extends AbstractEntity {
}
