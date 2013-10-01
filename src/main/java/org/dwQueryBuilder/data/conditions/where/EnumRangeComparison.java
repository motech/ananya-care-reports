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
