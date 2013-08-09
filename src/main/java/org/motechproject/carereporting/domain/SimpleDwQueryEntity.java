package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "simple_dw_query")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "simple_dw_query_id"))
})
public class SimpleDwQueryEntity extends DwQueryEntity {

    @Column(name = "table_name")
    private String tableName;

    @ManyToMany
    @JoinTable(name = "simple_dw_query_condition", joinColumns = { @JoinColumn(name = "simple_dw_query_id") },
            inverseJoinColumns = { @JoinColumn(name = "condition_id") })
    private Set<ConditionEntity> whereConditions;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Set<ConditionEntity> getWhereConditions() {
        return whereConditions;
    }

    public void setWhereConditions(Set<ConditionEntity> whereConditions) {
        this.whereConditions = whereConditions;
    }
}
