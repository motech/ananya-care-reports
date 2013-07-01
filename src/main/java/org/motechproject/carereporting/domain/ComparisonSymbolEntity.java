package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comparison_symbol")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "comparison_symbol_id"))
})
public class ComparisonSymbolEntity extends AbstractEntity {
}
