package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "chart_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "chart_type_id"))
})
public class ChartTypeEntity extends AbstractEntity {
}
