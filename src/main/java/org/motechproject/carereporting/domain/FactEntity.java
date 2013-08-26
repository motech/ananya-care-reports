package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fact")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "fact_id"))
})
public class FactEntity extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "table_id", referencedColumnName = "dw_query_id")
    private SimpleDwQueryEntity table;

    @Column(name = "combine_type")
    private String combineType;

    public FactEntity() {
    }

    public FactEntity(FactEntity factEntity) {
        combineType = factEntity.getCombineType();
        table = factEntity.getTable() != null ? new SimpleDwQueryEntity(factEntity.getTable()) : null;
    }

    public SimpleDwQueryEntity getTable() {
        return table;
    }

    public void setTable(SimpleDwQueryEntity table) {
        this.table = table;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }
}
