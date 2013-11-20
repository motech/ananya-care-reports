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
    private Integer offset1;

    @Column(name = "end_offset")
    @JsonView({ QueryJsonView.EditForm.class })
    private Integer offset2;

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
        offset1 = conditionEntity.getOffset1();
        offset2 = conditionEntity.getOffset2();
        tableName = conditionEntity.getTableName();
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getOffset1() {
        return offset1;
    }

    public void setOffset1(Integer offset1) {
        this.offset1 = offset1;
    }

    public Integer getOffset2() {
        return offset2;
    }

    public void setOffset2(Integer offset2) {
        this.offset2 = offset2;
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
        periodConditionEntity.setOffset1(this.getOffset1());
        periodConditionEntity.setOffset2(this.getOffset2());
        periodConditionEntity.setTableName(this.getTableName());
        periodConditionEntity.setColumnName(this.getColumnName());

        return periodConditionEntity;
    }
}
