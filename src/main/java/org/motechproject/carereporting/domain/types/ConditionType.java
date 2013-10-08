package org.motechproject.carereporting.domain.types;

public enum ConditionType {

    FieldComparison,
    ValueComparison,
    DateDiffComparison,
    DateRangeComparison,
    DateValueComparison,
    EnumRangeComparison,
    Period,
    CalculationEndDate;

    public static final String FIELD_COMPARISON = "field";
    public static final String VALUE_COMPARISON = "value";
    public static final String DATE_DIFF_COMPARISON = "dateDiff";
    public static final String DATE_RANGE_COMPARISON = "dateRange";
    public static final String DATE_VALUE_COMPARISON = "dateValue";
    public static final String ENUM_RANGE_COMPARISON = "enumRange";
    public static final String PERIOD = "period";
    public static final String CALCULATION_END_DATE = "calculationEndDate";

    private String value;

    static {
        FieldComparison.value = FIELD_COMPARISON;
        ValueComparison.value = VALUE_COMPARISON;
        DateDiffComparison.value = DATE_DIFF_COMPARISON;
        DateRangeComparison.value = DATE_RANGE_COMPARISON;
        DateValueComparison.value = DATE_VALUE_COMPARISON;
        EnumRangeComparison.value = ENUM_RANGE_COMPARISON;
        Period.value = PERIOD;
        CalculationEndDate.value = CALCULATION_END_DATE;
    }

    public String getValue() {
        return value;
    }
}
