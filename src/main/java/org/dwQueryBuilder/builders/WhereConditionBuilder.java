package org.dwQueryBuilder.builders;

import org.apache.commons.lang.StringUtils;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.DateDiffComparison;
import org.dwQueryBuilder.data.conditions.where.DateRangeComparison;
import org.dwQueryBuilder.data.conditions.where.DateValueComparison;
import org.dwQueryBuilder.data.conditions.where.EnumRangeComparison;
import org.dwQueryBuilder.data.conditions.where.FieldComparison;
import org.dwQueryBuilder.data.conditions.where.ValueComparison;
import org.dwQueryBuilder.data.conditions.where.WhereCondition;
import org.dwQueryBuilder.data.enums.ComparisonType;
import org.dwQueryBuilder.exceptions.QueryBuilderRuntimeException;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class WhereConditionBuilder {

    private SelectColumn selectColumn1;
    private ComparisonType operator;
    private SelectColumn selectColumn2;
    private String value;
    private String date1;
    private String date2;
    private String column1Offset;
    private String column2Offset;
    private Set<String> values;

    public WhereConditionBuilder withValueComparison(String table1Name, String column1Name,
                                                     ComparisonType operator,
                                                     String value) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.operator = operator;
        this.value = value;

        return this;
    }

    public WhereConditionBuilder withValueComparison(SelectColumn selectColumn1,
                                                     ComparisonType operator,
                                                     String value) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.operator = operator;
        this.value = value;

        return this;
    }

    public WhereConditionBuilder withValueComparison(SelectColumnBuilder selectColumn1Builder,
                                                     ComparisonType operator,
                                                     String value) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.operator = operator;
        this.value = value;

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(String table1Name, String column1Name,
                                                         ComparisonType operator,
                                                         String value) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.operator = operator;
        this.value = value;
        this.column1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(SelectColumn selectColumn1,
                                                         ComparisonType operator,
                                                         String value) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.operator = operator;
        this.value = value;
        this.column1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(SelectColumnBuilder selectColumn1Builder,
                                                         ComparisonType operator,
                                                         String value) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.operator = operator;
        this.value = value;
        this.column1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(String table1Name, String column1Name,
                                                         ComparisonType operator,
                                                         String value,
                                                         Integer offset) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.operator = operator;
        this.value = value;
        this.column1Offset = offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(SelectColumn selectColumn1,
                                                         ComparisonType operator,
                                                         String value,
                                                         Integer offset) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.operator = operator;
        this.value = value;
        this.column1Offset = offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateValueComparison(SelectColumnBuilder selectColumn1Builder,
                                                         ComparisonType operator,
                                                         String value,
                                                         Integer offset) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.operator = operator;
        this.value = value;
        this.column1Offset = offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(String table1Name, String field1Name,
                                                        ComparisonType operator,
                                                        String table2Name, String field2Name,
                                                        Integer seconds) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, field1Name);
        this.operator = operator;
        this.selectColumn2 = new SelectColumn(table2Name, field2Name);
        this.value = seconds.toString();
        this.column1Offset = "0";
        this.column2Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(SelectColumn selectColumn1,
                                                        ComparisonType operator,
                                                        SelectColumn selectColumn2,
            Integer seconds) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.operator = operator;
        this.selectColumn2 = selectColumn2;
        this.value = seconds.toString();
        this.column1Offset = "0";
        this.column2Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(SelectColumnBuilder selectColumn1Builder,
                                                        ComparisonType operator,
                                                        SelectColumnBuilder selectColumn2Builder,
                                                        Integer seconds) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.operator = operator;
        this.selectColumn2 = selectColumn2Builder.build();
        this.value = seconds.toString();
        this.column1Offset = "0";
        this.column2Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(String table1Name, String column1Name,
                                                        ComparisonType operator,
                                                        String table2Name, String column2Name,
                                                        Integer seconds,
                                                        Integer column1Offset,
                                                        Integer column2Offset) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.operator = operator;
        this.selectColumn2 = new SelectColumn(table2Name, column2Name);
        this.value = seconds.toString();
        this.column1Offset = column1Offset.toString();
        this.column2Offset = column2Offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(SelectColumn selectColumn1,
                                                        ComparisonType operator,
                                                        SelectColumn selectColumn2,
                                                        Integer seconds,
                                                        Integer column1Offset,
                                                        Integer column2Offset) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.operator = operator;
        this.selectColumn2 = selectColumn2;
        this.value = seconds.toString();
        this.column1Offset = column1Offset.toString();
        this.column2Offset = column2Offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateDiffComparison(SelectColumnBuilder selectColumn1Builder,
                                                        ComparisonType operator,
                                                        SelectColumnBuilder selectColumn2Builder,
                                                        Integer seconds,
                                                        Integer column1Offset,
                                                        Integer column2Offset) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.operator = operator;
        this.selectColumn2 = selectColumn2Builder.build();
        this.value = seconds.toString();
        this.column1Offset = column1Offset.toString();
        this.column2Offset = column2Offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateRangeComparison(String table1Name, String column1Name,
                                                         String date1,
                                                         String date2) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateRangeComparison(SelectColumn selectColumn1,
                                                         String date1,
                                                         String date2) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateRangeComparison(SelectColumnBuilder selectColumn1Builder,
                                                         String date1,
                                                         String date2) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = "0";

        return this;
    }

    public WhereConditionBuilder withDateRangeComparison(String table1Name, String column1Name,
                                                         String date1,
                                                         String date2,
                                                         Integer column1Offset) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = column1Offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateRangeComparison(SelectColumn selectColumn1,
                                                         String date1,
                                                         String date2,
                                                         Integer column1Offset) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = column1Offset.toString();

        return this;
    }

    public WhereConditionBuilder withDateRangeComparison(SelectColumnBuilder selectColumn1Builder,
                                                         String date1,
                                                         String date2,
                                                         Integer column1Offset) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.date1 = date1;
        this.date2 = date2;
        this.column1Offset = column1Offset.toString();

        return this;
    }

    public WhereConditionBuilder withFieldComparison(String table1Name, String column1Name,
                                                     ComparisonType operator,
                                                     String table2Name, String column2Name) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.operator = operator;
        this.selectColumn2 = new SelectColumn(table2Name, column2Name);

        return this;
    }

    public WhereConditionBuilder withFieldComparison(SelectColumn selectColumn1,
                                                     ComparisonType operator,
                                                     SelectColumn selectColumn2) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.operator = operator;
        this.selectColumn2 = selectColumn2;

        return this;
    }

    public WhereConditionBuilder withFieldComparison(SelectColumnBuilder selectColumn1Builder,
                                                     ComparisonType operator,
                                                     SelectColumnBuilder selectColumn2Builder) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.operator = operator;
        this.selectColumn2 = selectColumn2Builder.build();

        return this;
    }

    public WhereConditionBuilder withEnumRangeComparison(String table1Name, String column1Name,
                                                         Collection<String> values) {
        this.reset();

        this.selectColumn1 = new SelectColumn(table1Name, column1Name);
        this.values = new LinkedHashSet<>(values);

        return this;
    }

    public WhereConditionBuilder withEnumRangeComparison(SelectColumn selectColumn1,
                                                         Collection<String> values) {
        this.reset();

        this.selectColumn1 = selectColumn1;
        this.values = new LinkedHashSet<>(values);

        return this;
    }

    public WhereConditionBuilder withEnumRangeComparison(SelectColumnBuilder selectColumn1Builder,
                                                         Collection<String> values) {
        this.reset();

        this.selectColumn1 = selectColumn1Builder.build();
        this.values = new LinkedHashSet<>(values);

        return this;
    }

    private void reset() {
        this.selectColumn1 = null;
        this.selectColumn2 = null;
        this.operator = null;
        this.value = null;
        this.date1 = null;
        this.date2 = null;
        this.column1Offset = null;
        this.column2Offset = null;
    }

    public WhereCondition build() {
        try {
            WhereCondition whereCondition = null;

            if (isDateDiffComparison()) {
                // Date Diff Comparison With Optional Offsets

                whereCondition = new DateDiffComparison(
                        selectColumn1,
                        operator,
                        selectColumn2,
                        value,
                        column1Offset,
                        column2Offset);

            } else if (isDateRangeComparison()) {
                // Date Range comparison With Optional Offset

                whereCondition = new DateRangeComparison(
                        selectColumn1,
                        date1,
                        date2,
                        Integer.parseInt(column1Offset));

            } else if (isDateValueComparison()) {
                // Date Value Comparison With Optional Offset

                whereCondition = new DateValueComparison(
                        selectColumn1,
                        operator,
                        value,
                        Integer.parseInt(column1Offset));

            } else if (isValueComparison()) {
                // Value Comparison

                whereCondition = new ValueComparison(
                        selectColumn1,
                        operator,
                        value);

            } else if (isFieldComparison()) {
                // Field Comparison

                whereCondition = new FieldComparison(
                        selectColumn1,
                        operator,
                        selectColumn2);

            } else if (isEnumRangeComparison()) {
                // Enum Range Comparison

                whereCondition = new EnumRangeComparison(
                        selectColumn1,
                        values);

            }

            return whereCondition;
        } catch (Exception e) {
            throw new QueryBuilderRuntimeException(e);
        }
    }

    private Boolean isDateDiffComparison() {
        Boolean iscolumn1NotBlank = selectColumn1 != null
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getFieldName());
        Boolean iscolumn2NotBlank = selectColumn2 != null
                && StringUtils.isNotBlank(selectColumn2.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn2.getComputedColumn().getFieldName());
        Boolean isValueValid = StringUtils.isNotBlank(value) && StringUtils.isNumeric(value);

        return (iscolumn1NotBlank
                && iscolumn2NotBlank
                && operator != null
                && isValueValid);
    }

    private Boolean isDateRangeComparison() {
        Boolean iscolumn1NotBlank = selectColumn1 != null
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getFieldName());
        Boolean isDateRangeValid = StringUtils.isNotBlank(date1) && StringUtils.isNotBlank(date2);
        Boolean iscolumn1OffsetValid = StringUtils.isNotBlank(column1Offset);

        return (iscolumn1NotBlank
                && isDateRangeValid
                && iscolumn1OffsetValid);
    }

    private Boolean isDateValueComparison() {
        Boolean iscolumn1NotBlank = selectColumn1 != null
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getFieldName());
        Boolean isComparisonValid = operator != null && StringUtils.isNotBlank(value)
                && StringUtils.isNotBlank(column1Offset);

        return (iscolumn1NotBlank
                && isComparisonValid);
    }

    private Boolean isValueComparison() {
        Boolean iscolumn1NotBlank = selectColumn1 != null
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getFieldName());

        return (iscolumn1NotBlank
                && operator != null
                && StringUtils.isNotBlank(value));
    }

    private Boolean isFieldComparison() {
        Boolean iscolumn1NotBlank = selectColumn1 != null
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getFieldName());
        Boolean iscolumn2NotBlank = selectColumn2 != null
                && StringUtils.isNotBlank(selectColumn2.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn2.getComputedColumn().getFieldName());

        return (iscolumn1NotBlank
                && iscolumn2NotBlank
                && operator != null);
    }

    private Boolean isEnumRangeComparison() {
        Boolean iscolumn1NotBlank = selectColumn1 != null
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getTableName())
                && StringUtils.isNotBlank(selectColumn1.getComputedColumn().getFieldName());

        return (iscolumn1NotBlank
                && values != null);
    }
}
