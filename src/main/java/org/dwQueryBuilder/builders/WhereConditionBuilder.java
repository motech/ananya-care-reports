package org.dwQueryBuilder.builders;

import org.apache.commons.lang.StringUtils;
import org.dwQueryBuilder.data.conditions.where.DateDiffComparison;
import org.dwQueryBuilder.data.conditions.where.DateValueComparison;
import org.dwQueryBuilder.data.conditions.where.ValueComparison;
import org.dwQueryBuilder.data.conditions.where.WhereCondition;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.exceptions.QueryBuilderException;

public class WhereConditionBuilder {

    // TODO : Implement fieldComparison, dateRange, enumRange(CARE #71?)

    private String table1Name;
    private String field1Name;
    private OperatorType operator;
    private String table2Name;
    private String field2Name;
    private String value;
    private String field1Offset;
    private String field2Offset;

    public WhereConditionBuilder withValueComparison(String table1Name,
            String field1Name, OperatorType operator, String value) {
        this.reset();

        this.table1Name = table1Name;
        this.field1Name = field1Name;
        this.operator = operator;
        this.value = value;

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(String table1Name,
            String field1Name, OperatorType operator, String value) {
        this.reset();

        this.table1Name = table1Name;
        this.field1Name = field1Name;
        this.operator = operator;
        this.value = value;
        this.field1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(String table1Name,
            String field1Name, OperatorType operator, String value, Integer offset) {
        this.reset();

        this.table1Name = table1Name;
        this.field1Name = field1Name;
        this.operator = operator;
        this.value = value;
        this.field1Offset = offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(String table1Name,
            String field1Name, OperatorType operator, String table2Name, String field2Name,
            Integer seconds) {
        this.reset();

        this.table1Name = table1Name;
        this.field1Name = field1Name;
        this.operator = operator;
        this.table2Name = table2Name;
        this.field2Name = field2Name;
        this.value = seconds.toString();
        this.field1Offset = "0";
        this.field2Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(String table1Name,
            String field1Name, OperatorType operator, String table2Name, String field2Name,
            Integer seconds, Integer field1Offset, Integer field2Offset) {
        this.reset();

        this.table1Name = table1Name;
        this.field1Name = field1Name;
        this.operator = operator;
        this.table2Name = table2Name;
        this.field2Name = field2Name;
        this.value = seconds.toString();
        this.field1Offset = field1Offset.toString();
        this.field2Offset = field2Offset.toString();

        return this;
    }

    private void reset() {
        this.table1Name = null;
        this.field1Name = null;
        this.table2Name = null;
        this.field2Name = null;
        this.operator = null;
        this.value = null;
        this.field1Offset = null;
        this.field2Offset = null;
    }

    public WhereCondition build() {
        try {
            WhereCondition whereCondition = null;

            if (isDateDiffComparison()) {
                // Date Diff Comparison With Optional Offsets

                whereCondition = new DateDiffComparison(table1Name, field1Name,
                        operator, table2Name, field2Name, value, field1Offset, field2Offset);

            } else if (isDateValueComparison()) {
                // Date Value Comparison With Optional Offset

                whereCondition = new DateValueComparison(table1Name, field1Name,
                        operator, value, Integer.parseInt(field1Offset));

            } else if (isValueComparison()) {
                // Value Comparison

                whereCondition = new ValueComparison(table1Name, field1Name,
                        operator, value);

            }

            return whereCondition;
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }

    private Boolean isDateDiffComparison() {
        Boolean isField1NotBlank = StringUtils.isNotBlank(table1Name) && StringUtils.isNotBlank(field1Name);
        Boolean isField2NotBlank = StringUtils.isNotBlank(table2Name) && StringUtils.isNotBlank(field2Name);
        Boolean isValueValid = StringUtils.isNotBlank(value) && StringUtils.isNumeric(value);

        return (isField1NotBlank
                && isField2NotBlank
                && operator != null
                && isValueValid);
    }

    private Boolean isDateValueComparison() {
        Boolean isComparisonValid = operator != null && StringUtils.isNotBlank(value)
                && StringUtils.isNotBlank(field1Offset);

        return (StringUtils.isNotBlank(table1Name)
                && StringUtils.isNotBlank(field1Name)
                && isComparisonValid);
    }

    private Boolean isValueComparison() {
        return (StringUtils.isNotBlank(table1Name)
                && StringUtils.isNotBlank(field1Name)
                && operator != null
                && StringUtils.isNotBlank(value));
    }
}
