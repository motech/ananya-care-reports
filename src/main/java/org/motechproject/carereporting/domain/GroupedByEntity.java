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
@Table(name = "grouped_by")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "grouped_by_id"))
})
public class GroupedByEntity extends AbstractEntity {

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "field_name")
    private String fieldName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "having_id", referencedColumnName = "having_id")
    private HavingEntity having;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public HavingEntity getHaving() {
        return having;
    }

    public void setHaving(HavingEntity having) {
        this.having = having;
    }
}
