package org.motechproject.carereporting.calculator.condition;

import org.junit.Test;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.DateDiffComparisonConditionEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.motechproject.carereporting.indicator.condition.DateDiffComparisonWhereCondition;
import org.motechproject.carereporting.indicator.condition.ValueComparisonWhereCondition;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateDiffComparisonWhereConditionTest {

    private static final String COMPUTED_FIELD_SQL = "(date_of_birth - date_of_death)";
    private static final String COMPUTED_FIELD_2_SQL = "some_other_date_field";
    private static final Integer CONDITION_VALUE = 200;
    private static final String COMPARISON_SYMBOL_NAME = ">=";
    private static final String EXPECTED_SQL = "(((date_of_birth - date_of_death) - some_other_date_field) >= 200)";

    @Test
    public void testGetSqlQuery() {
        DateDiffComparisonConditionEntity condition = mock(DateDiffComparisonConditionEntity.class);
        ComputedFieldEntity computedField = mock(ComputedFieldEntity.class);
        when(computedField.getFieldSql()).thenReturn(COMPUTED_FIELD_SQL);
        ComparisonSymbolEntity comparisonSymbol = mock(ComparisonSymbolEntity.class);
        when(comparisonSymbol.getName()).thenReturn(COMPARISON_SYMBOL_NAME);
        when(condition.getComparisonSymbol()).thenReturn(comparisonSymbol);
        when(condition.getValue()).thenReturn(CONDITION_VALUE);
        ComputedFieldEntity computedField2 = mock(ComputedFieldEntity.class);
        when(computedField2.getFieldSql()).thenReturn(COMPUTED_FIELD_2_SQL);
        when(condition.getField2()).thenReturn(computedField2);

        DateDiffComparisonWhereCondition whereCondition = new DateDiffComparisonWhereCondition(computedField, condition);
        assertEquals(EXPECTED_SQL, whereCondition.getSql());
    }

}
