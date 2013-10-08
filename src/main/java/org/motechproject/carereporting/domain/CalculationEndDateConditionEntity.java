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
@Table(name = "calculation_end_date_comparison")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "calculation_end_date_comparison_id"))
})
@JsonTypeName(value = "calculation_end_date_comparison")
@SuppressWarnings("CPD-START")
public class CalculationEndDateConditionEntity extends ConditionEntity {

    @Column(name = "column_name")
    @JsonView({ QueryJsonView.EditForm.class })
    private String columnName;

    @Column(name = "date_offset")
    @JsonView({ QueryJsonView.EditForm.class })
    private Integer offset;

    @Column(name = "table_name")
    @JsonView({ QueryJsonView.EditForm.class })
    private String tableName;

    public CalculationEndDateConditionEntity() {
    }

    @Override
    public String getType() {
        return ConditionType.CalculationEndDate.getValue();
    }

    public CalculationEndDateConditionEntity(CalculationEndDateConditionEntity conditionEntity) {
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

    @SuppressWarnings("CPD-END")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        CalculationEndDateConditionEntity calculationEndDateConditionEntity = new CalculationEndDateConditionEntity();

        calculationEndDateConditionEntity.setField1(this.getField1());
        calculationEndDateConditionEntity.setOffset(this.getOffset());
        calculationEndDateConditionEntity.setTableName(this.getTableName());
        calculationEndDateConditionEntity.setColumnName(this.getColumnName());

        return calculationEndDateConditionEntity;
    }
}
