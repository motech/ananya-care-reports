package org.dwQueryBuilder.builders;

import org.apache.commons.lang.StringUtils;
import org.dwQueryBuilder.data.conditions.where.ValueComparison;
import org.dwQueryBuilder.data.conditions.where.WhereCondition;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.exceptions.QueryBuilderException;

public class WhereConditionBuilder {

    // TODO : Uncomment lines after implementing remaining comparison types

    private String table1Name;
    private String field1Name;
//    private String table2Name;
//    private String field2Name;
    private OperatorType operator;
    private String value;

    public WhereConditionBuilder withValueComparison(String table1Name,
            String field1Name, OperatorType operator, String value) {
        this.reset();

        this.table1Name = table1Name;
        this.field1Name = field1Name;
        this.operator = operator;
        this.value = value;

        return this;
    }

    private void reset() {
        this.table1Name = null;
        this.field1Name = null;
//        this.table2Name = null;
//        this.field2Name = null;
        this.operator = null;
        this.value = null;
    }

    @SuppressWarnings("unchecked")
    public <T extends WhereCondition> T build() {
        try {
            WhereCondition whereCondition = null;

            if (StringUtils.isNotBlank(table1Name)
                    && StringUtils.isNotBlank(field1Name)
                    && operator != null
                    && StringUtils.isNotBlank(value)) {
                whereCondition = new ValueComparison(table1Name, field1Name,
                        operator, value);
            }

            return (T) whereCondition;
        } catch (Exception e) {
            throw new QueryBuilderException(e);
        }
    }
}
