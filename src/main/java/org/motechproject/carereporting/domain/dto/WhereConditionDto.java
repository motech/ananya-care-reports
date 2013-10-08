package org.motechproject.carereporting.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.views.QueryJsonView;

import java.util.Date;
import java.util.List;

public class WhereConditionDto {

    @JsonView({ QueryJsonView.EditForm.class })
    private ComputedFieldEntity field1;

    @JsonView({ QueryJsonView.EditForm.class })
    private ComputedFieldEntity field2;

    @JsonView({ QueryJsonView.EditForm.class })
    private Integer offset1;

    @JsonView({ QueryJsonView.EditForm.class })
    private Integer offset2;

    @JsonView({ QueryJsonView.EditForm.class })
    private String operator;

    @JsonView({ QueryJsonView.EditForm.class })
    private String type;

    @JsonView({ QueryJsonView.EditForm.class })
    private String value;

    @JsonView({ QueryJsonView.EditForm.class })
    private List<String> values;

    @JsonView({ QueryJsonView.EditForm.class })
    private Date date1;

    @JsonView({ QueryJsonView.EditForm.class })
    private Date date2;
    
    public WhereConditionDto() {
        
    }

    public WhereConditionDto(ComputedFieldEntity field1, ComputedFieldEntity field2, Integer offset1,
                             Integer offset2, String operator, String type, String value, List<String> values,
                             Date date1, Date date2) {
        this.field1 = field1;
        this.field2 = field2;
        this.offset1 = offset1;
        this.offset2 = offset2;
        this.operator = operator;
        this.type = type;
        this.value = value;
        this.values = values;
        this.date1 = date1;
        this.date2 = date2;
    }

    public ComputedFieldEntity getField1() {
        return field1;
    }

    public void setField1(ComputedFieldEntity field1) {
        this.field1 = field1;
    }

    public ComputedFieldEntity getField2() {
        return field2;
    }

    public void setField2(ComputedFieldEntity field2) {
        this.field2 = field2;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
