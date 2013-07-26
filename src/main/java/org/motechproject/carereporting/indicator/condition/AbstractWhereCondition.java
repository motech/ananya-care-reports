package org.motechproject.carereporting.indicator.condition;

import org.apache.commons.lang.text.StrSubstitutor;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.ConditionEntity;

import java.util.Map;

public abstract class AbstractWhereCondition<E extends ConditionEntity> {

    protected ComputedFieldEntity field;
    protected E condition;

    public AbstractWhereCondition(ComputedFieldEntity field, E condition) {
        this.field = field;
        this.condition = condition;
    }

    public String getSql() {
        return format(getSqlQuery(), getSqlQueryParams());
    }

    protected abstract String getSqlQuery();
    protected abstract Map<String, String> getSqlQueryParams();

    private String format(String strToFormat, Map<String, String> params) {
        return new StrSubstitutor(params, "%(", ")").replace(strToFormat);
    }

}
