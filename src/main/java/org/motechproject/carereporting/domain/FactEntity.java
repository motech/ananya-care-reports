package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fact")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "fact_id"))
})
public class FactEntity extends AbstractEntity {

    private SimpleDwQueryEntity table;

    @Column(name = "combine_type")
    private String combineType;

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
