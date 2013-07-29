package org.motechproject.carereporting.calculator.condition;

import org.junit.Test;
import org.mockito.Mockito;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;
import org.motechproject.carereporting.indicator.condition.FieldComparisonWhereCondition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldComparisonWhereConditionTest {

    private static final String COMPUTED_FIELD_SQL = "(field1 - field2)";
    private static final String COMPUTED_FIELD_2_SQL = "(field3 * field4)";
    private static final String COMPARISON_SYMBOL_NAME = ">";
    private static final String EXPECTED_SQL = "((field1 - field2) > (field3 * field4))";

    @Test
    public void testGetSqlQuery() {
        FieldComparisonConditionEntity condition = mock(FieldComparisonConditionEntity.class);
        ComputedFieldEntity computedField = mock(ComputedFieldEntity.class);
        when(computedField.getFieldSql()).thenReturn(COMPUTED_FIELD_SQL);
        ComparisonSymbolEntity comparisonSymbol = mock(ComparisonSymbolEntity.class);
        when(comparisonSymbol.getName()).thenReturn(COMPARISON_SYMBOL_NAME);
        when(condition.getComparisonSymbol()).thenReturn(comparisonSymbol);
        ComputedFieldEntity computedField2 = mock(ComputedFieldEntity.class);
        when(computedField2.getFieldSql()).thenReturn(COMPUTED_FIELD_2_SQL);
        when(condition.getField2()).thenReturn(computedField2);

        FieldComparisonWhereCondition whereCondition = new FieldComparisonWhereCondition(computedField, condition);
        assertEquals(EXPECTED_SQL, whereCondition.getSql());
    }

}
