package org.motechproject.carereporting.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.types.ConditionType;
import org.motechproject.carereporting.domain.views.QueryJsonView;

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
    @JsonView({ QueryJsonView.EditForm.class })
    private String columnName;

    @Column(name = "start_offset")
    @JsonView({ QueryJsonView.EditForm.class })
    private Integer offset;

    @Column(name = "table_name")
    @JsonView({ QueryJsonView.EditForm.class })
    private String tableName;

    public PeriodConditionEntity() {
    }

    @Override
    public String getType() {
        return ConditionType.Period.getValue();
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        PeriodConditionEntity periodConditionEntity = new PeriodConditionEntity();

        periodConditionEntity.setField1(this.getField1());
        periodConditionEntity.setOffset(this.getOffset());
        periodConditionEntity.setTableName(this.getTableName());
        periodConditionEntity.setColumnName(this.getColumnName());

        return periodConditionEntity;
    }
}
