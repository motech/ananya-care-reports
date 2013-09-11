package org.motechproject.carereporting.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "select_column")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "select_column_id"))
})
public class SelectColumnEntity extends AbstractEntity {

    @Column(name = "function")
    private String functionName;

    @ManyToOne
    @JoinColumn(name = "computed_field_id")
    private ComputedFieldEntity computedField;

    @Column(name = "null_value")
    private String nullValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dw_query_id", referencedColumnName = "dw_query_id")
    private DwQueryEntity dwQuery;

    public SelectColumnEntity() {
    }

    public SelectColumnEntity(SelectColumnEntity selectColumnEntity) {
        functionName = selectColumnEntity.getFunctionName();
        computedField = selectColumnEntity.getComputedField();
        dwQuery = selectColumnEntity.getDwQuery();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public ComputedFieldEntity getComputedField() {
        return computedField;
    }

    public void setComputedField(ComputedFieldEntity computedField) {
        this.computedField = computedField;
    }

    public String getNullValue() {
        return nullValue;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public DwQueryEntity getDwQuery() {
        return dwQuery;
    }

    public void setDwQuery(DwQueryEntity dwQuery) {
        this.dwQuery = dwQuery;
    }
}
