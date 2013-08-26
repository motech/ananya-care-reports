package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "simple_dw_query")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "simple_dw_query_id"))
})
public class SimpleDwQueryEntity extends DwQueryEntity {

    @Column(name = "table_name")
    private String tableName;

    public SimpleDwQueryEntity() {
    }

    public SimpleDwQueryEntity(SimpleDwQueryEntity dwQueryEntity) {
        super(dwQueryEntity);
        tableName = dwQueryEntity.getTableName();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
