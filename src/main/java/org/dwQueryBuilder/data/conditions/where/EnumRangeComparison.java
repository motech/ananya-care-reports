package org.dwQueryBuilder.data.conditions.where;

import org.dwQueryBuilder.data.SelectColumn;

import java.util.LinkedHashSet;
import java.util.Set;

public class EnumRangeComparison extends WhereCondition {

    private Set<String> values;

    public EnumRangeComparison() {
        super();

        this.values = new LinkedHashSet<>();
    }

    public EnumRangeComparison(String table1Name, String field1Name) {
        super(table1Name, field1Name);

        this.values = new LinkedHashSet<>();
    }

    public EnumRangeComparison(SelectColumn selectColumn1) {
        super(selectColumn1);

        this.values = new LinkedHashSet<>();
    }

    public EnumRangeComparison(String table1Name, String field1Name,
                               Set<String> values) {
        super(table1Name, field1Name);

        this.values = values;
    }

    public EnumRangeComparison(SelectColumn selectColumn1,
                               Set<String> values) {
        super(selectColumn1);

        this.values = values;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }
}
