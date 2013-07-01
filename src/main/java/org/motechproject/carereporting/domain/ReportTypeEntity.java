package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "report_type")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "report_type_id"))
})
public class ReportTypeEntity extends AbstractEntity {
}
