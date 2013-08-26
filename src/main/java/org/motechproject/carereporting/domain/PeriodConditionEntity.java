package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "period_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "period_comparison_id"))
})
@JsonTypeName(value = "period")
public class PeriodConditionEntity extends ConditionEntity {

    @Column(name = "column_name")
    private String columnName;

    @Column(name = "start_offset")
    private Integer offset;

    @Column(name = "table_name")
    private String tableName;

    public PeriodConditionEntity() {
    }

    public PeriodConditionEntity(PeriodConditionEntity conditionEntity) {
        super(conditionEntity);
        columnName = conditionEntity.getColumnName();
        offset = conditionEntity.getOffset();
        tableName = conditionEntity.getTableName();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
