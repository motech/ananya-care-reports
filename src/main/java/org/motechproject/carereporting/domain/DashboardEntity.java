package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "dashboard")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "dashboard_id"))
})
public class DashboardEntity extends AbstractEntity {
}
