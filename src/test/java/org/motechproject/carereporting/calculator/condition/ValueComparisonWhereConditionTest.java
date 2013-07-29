package org.motechproject.carereporting.calculator.condition;

import org.junit.Test;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.motechproject.carereporting.indicator.condition.FieldComparisonWhereCondition;
import org.motechproject.carereporting.indicator.condition.ValueComparisonWhereCondition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValueComparisonWhereConditionTest {

    private static final String COMPUTED_FIELD_SQL = "(field1 - field2)";
    private static final String CONDITION_VALUE = "200";
    private static final String COMPARISON_SYMBOL_NAME = ">";
    private static final String EXPECTED_SQL = "((field1 - field2) > 200)";

    @Test
    public void testGetSqlQuery() {
        ValueComparisonConditionEntity condition = mock(ValueComparisonConditionEntity.class);
        ComputedFieldEntity computedField = mock(ComputedFieldEntity.class);
        when(computedField.getFieldSql()).thenReturn(COMPUTED_FIELD_SQL);
        ComparisonSymbolEntity comparisonSymbol = mock(ComparisonSymbolEntity.class);
        when(comparisonSymbol.getName()).thenReturn(COMPARISON_SYMBOL_NAME);
        when(condition.getComparisonSymbol()).thenReturn(comparisonSymbol);
        when(condition.getValue()).thenReturn(CONDITION_VALUE);

        ValueComparisonWhereCondition whereCondition = new ValueComparisonWhereCondition(computedField, condition);
        assertEquals(EXPECTED_SQL, whereCondition.getSql());
    }

}
