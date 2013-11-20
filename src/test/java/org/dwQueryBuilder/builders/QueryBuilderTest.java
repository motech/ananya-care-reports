package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.ComputedColumn;
import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.OrderBy;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;
import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.enums.ComparisonType;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.data.enums.OrderByType;
import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;
import org.dwQueryBuilder.data.enums.WhereConditionJoinType;
import org.dwQueryBuilder.data.DwQuery;
import org.jooq.SQLDialect;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class QueryBuilderTest {

    private static final String WILDCARD = "*";
    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String TEST_SCHEMA_NAME = "report";
    private static final String BP_FORM = "bp_form";
    private static final String IFA_TABLETS_ISSUED = "ifa_tablets_issued";
    private static final String IFA_TABLETS_TOTAL = "ifa_tablets_total";
    private static final String CLOSE_MOTHER_FORM = "close_mother_form";
    private static final String CLOSE_CHILD_FORM = "close_child_form";
    private static final String CHILD_CASE = "child_case";
    private static final String MOTHER_ID = "mother_id";
    private static final String MOTHER_CASE = "mother_case";
    private static final String AGE = "age";
    private static final String AGE_COMPUTED_COLUMN_ALIAS = "age_alias";
    private static final String ADD = "add";
    private static final String EDD = "edd";
    private static final String USER_ID = "user_id";
    private static final String ID = "id";
    private static final String CASE_ID = "case_id";
    private static final String FLW = "flw";
    private static final String FLW_STATE = "state";
    private static final String FLW_STATE_NAME = "Bihar";
    private static final String TIME_START = "time_start";
    private static final String TIME_END = "time_end";
    private static final String FIRST_DATE = "02-02-2013";
    private static final String SECOND_DATE = "04-02-2013";
    private static final Integer DATE_OFFSET_1 = -120;
    private static final Integer DATE_OFFSET_2 = 360;
    private static final Integer DATE_OFFSET_TEN_DAYS = 10;
    private static final Integer DIFFERENCE = 500;
    private static final String AGE_1 = "26";
    private static final String AGE_2 = "23";
    private static final String AGE_3 = "19";

    private static final String EXPECTED_POSTGRESQL_SIMPLE_CONDITION_WITH_DATE_DIFF_SQL_STRING =
            "select count(\"report\".\"bp_form\".\"ifa_tablets_issued\") from \"report\".\"bp_form\" " +
                    "join (select * from \"report\".\"flw\") as \"flw\" on \"flw\".\"id\" = " +
                    "\"report\".\"bp_form\".\"user_id\" where (\"bp_form\".\"ifa_tablets_issued\" <= '120'" +
                    " or (\"bp_form\".\"time_end\" + '-120') - (\"bp_form\".\"time_start\" + '360') >= '500')";
    private static final String EXPECTED_POSTGRESQL_INDICATOR_NON_PERIOD_SPECIFIC_COUNT_OF_CASES_SQL_STRING =
            "select count(*) from \"report\".\"mother_case\" join (select \"report\".\"bp_form\".\"case_id\" " +
                    "from \"report\".\"bp_form\" group by \"report\".\"bp_form\".\"case_id\" having " +
                    "count(\"bp_form\".\"case_id\") >= '1') as \"bp_form\" on \"bp_form\".\"case_id\" = " +
                    "\"report\".\"mother_case\".\"id\" join (select * from \"report\".\"flw\") as " +
                    "\"flw\" on \"flw\".\"id\" = \"report\".\"mother_case\".\"user_id\" where " +
                    "\"flw\".\"state\" = 'Bihar'";
    private static final String EXPECTED_POSTGRESQL_INDICATOR_COUNT_OF_CASES_IN_A_PERIOD_DD_OFFSET_BASED =
            "select count(*) from \"report\".\"mother_case\" join (select * from \"report\".\"flw\") as \"flw\" on " +
                    "\"flw\".\"id\" = \"report\".\"mother_case\".\"user_id\" where ((((\"mother_case\".\"add\"" +
                    " + cast('+10 00:00:00.000000000' as interval day to second)) >= '02-02-2013' and " +
                    "(\"mother_case\".\"add\" + cast('+10 00:00:00.000000000' as interval day to second))" +
                    " <= '04-02-2013') or ((\"mother_case\".\"edd\" + cast('+10 00:00:00.000000000' " +
                    "as interval day to second)) >= '02-02-2013' and (\"mother_case\".\"edd\"" +
                    " + cast('+10 00:00:00.000000000' as interval day to second)) <= '04-02-2013'))" +
                    " and \"flw\".\"state\" = 'Bihar')";
    private static final String EXPECTED_POSTGRESQL_DATE_RANGE_COMPARISON_SQL_STRING =
            "select count(\"report\".\"bp_form\".\"ifa_tablets_issued\") from \"report\".\"bp_form\"" +
                    " where ((\"bp_form\".\"time_start\" + cast('+10 00:00:00.000000000' as interval" +
                    " day to second)) >= '02-02-2013' and (\"bp_form\".\"time_start\" + " +
                    "cast('+10 00:00:00.000000000' as interval day to second)) < '04-02-2013')";
    private static final String EXPECTED_POSTGRESQL_HAVING_COUNT_WILDCARD_SQL_STRING =
            "select count(\"report\".\"bp_form\".\"ifa_tablets_issued\") from \"report\".\"bp_form\" where " +
                    "((\"bp_form\".\"time_start\" + cast('+10 00:00:00.000000000' as interval day to second)) >= " +
                    "'02-02-2013' and (\"bp_form\".\"time_start\"" +
                    " + cast('+10 00:00:00.000000000' as interval day to second)) < '04-02-2013') " +
                    "group by \"report\".\"bp_form\".\"ifa_tablets_issued\" having count(\"bp_form\".*) >= '1'";
    private static final String EXPECTED_POSTGRESQL_FIELD_COMPARISON_SQL_STRING =
            "select count(\"report\".\"bp_form\".\"ifa_tablets_issued\") from \"report\".\"bp_form\" where " +
                    "(\"bp_form\".\"ifa_tablets_issued\" - 1) = (\"bp_form\".\"ifa_tablets_total\" + 1) " +
                    "group by \"report\".\"bp_form\".\"ifa_tablets_issued\" having count(\"bp_form\".*) >= '1'";
    private static final String EXPECTED_POSTGRESQL_MULTIPLE_JOINS_SQL_STRING =
            "select \"report\".\"mother_case\".\"id\" from \"report\".\"mother_case\" join " +
                    "(select * from \"report\".\"close_mother_form\") as \"close_mother_form\" on " +
                    "\"close_mother_form\".\"case_id\" = \"report\".\"mother_case\".\"id\" join " +
                    "(select * from \"report\".\"child_case\" join " +
                    "(select * from \"report\".\"close_child_form\") as \"close_child_form\" on " +
                    "\"close_child_form\".\"case_id\" = \"report\".\"child_case\".\"id\") as " +
                    "\"child_case\" on \"child_case\".\"mother_id\" = \"report\".\"mother_case\".\"id\" " +
                    "join (select * from \"report\".\"bp_form\") as \"bp_form\" on " +
                    "\"bp_form\".\"case_id\" = \"report\".\"mother_case\".\"id\"";
    private static final String EXPECTED_POSTGRESQL_ENUM_RANGE_COMPARISON =
            "select \"report\".\"mother_case\".\"age\" from \"report\".\"mother_case\" where " +
                    "\"mother_case\".\"age\" in ('26', '23', '19')";
    private static final String EXPECTED_POSTGRESQL_NVL_WITHOUT_AGGREGATE_FUNCTION_SQL_STRING =
            "select coalesce(\"report\".\"mother_case\".\"age\", '0') from \"report\".\"mother_case\"";
    private static final String EXPECTED_POSTGRESQL_NVL_WITH_AGGREGATE_FUNCTION_SQL_STRING =
            "select count(coalesce(\"report\".\"mother_case\".\"age\", '0')) from \"report\".\"mother_case\"";
    private static final String EXPECTED_POSTGRESQL_COMPUTED_COLUMN_WITH_CONDITION_SQL_STRING =
            "select (((\"report\".\"mother_case\".\"age\" + \"report\".\"mother_case\".\"age\") * " +
                    "(\"report\".\"mother_case\".\"age\" - \"report\".\"mother_case\".\"age\")) + " +
                    "\"report\".\"mother_case\".\"age\") as \"age_alias\" from \"report\".\"mother_case\" " +
                    "where (((\"mother_case\".\"age\" + \"mother_case\".\"age\") * " +
                    "(\"mother_case\".\"age\" - \"mother_case\".\"age\")) + \"mother_case\".\"age\") > '1'";
    private static final String EXPECTED_POSTGRESQL_COMPUTED_COLUMN_WITH_FUNCTIOM_SQL_STRING =
            "select max(coalesce((((\"report\".\"mother_case\".\"age\" + \"report\".\"mother_case\".\"age\") * " +
                    "(\"report\".\"mother_case\".\"age\" - \"report\".\"mother_case\".\"age\")) + " +
                    "\"report\".\"mother_case\".\"age\"), '0')) as \"age_alias\" from \"report\".\"mother_case\"";
    private static final String EXPECTED_POSTGRESQL_VALUE_TO_LOWER_CASE_SQL_STRING =
            "select count(\"report\".\"flw\".\"state\") from \"report\".\"flw\" where " +
                    "lower(cast(\"flw\".\"state\" as varchar)) = 'bihar'";
    private static final String EXPECTED_POSTGRESQL_LIMIT_SQL_STRING =
            "select * from \"report\".\"mother_case\" limit 1 offset 0";
    private static final String EXPECTED_POSTGRESQL_LIMIT_ZERO_SQL_STRING =
            "select * from \"report\".\"mother_case\" limit 0 offset 0";
    private static final String EXPECTED_POSTGRESQL_ORDER_BY_MULTIPLE_SQL_STRING =
            "select coalesce(\"report\".\"mother_case\".\"id\", '0'), coalesce(\"report\".\"mother_case\".\"age\", " +
                    "'0') from \"report\".\"mother_case\" order by coalesce(\"mother_case\".\"age\", '0') asc," +
                    " coalesce(\"mother_case\".\"id\", '0') desc";

    @Test
    public void testPostgreSqlQueryBuilderSimpleConditionWithDateDiffComparison() {
        DwQuery flwQuery = new DwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumn(WILDCARD)
                )
                .withTableName(FLW)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder().withColumn(BP_FORM, IFA_TABLETS_ISSUED)
                                .withFunction(SelectColumnFunctionType.Count))
                .withTableName(BP_FORM)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withGroup(
                                        new WhereConditionGroupBuilder()
                                                .withCondition(new WhereConditionBuilder()
                                                        .withValueComparison(BP_FORM, IFA_TABLETS_ISSUED,
                                                                ComparisonType.LessEqual, "120"))
                                )
                                .withGroup(
                                        new WhereConditionGroupBuilder()
                                                .withCondition(
                                                        new WhereConditionBuilder()
                                                                .withDateDiffComparison(
                                                                        BP_FORM, TIME_END,
                                                                        ComparisonType.GreaterEqual,
                                                                        BP_FORM, TIME_START,
                                                                        DIFFERENCE,
                                                                        DATE_OFFSET_1,
                                                                        DATE_OFFSET_2))
                                                .withJoinType(WhereConditionJoinType.OR)
                                )
                )
                .withCombination(
                        new DwQueryCombinationBuilder()
                                .withDwQuery(flwQuery)
                                .withCombineType(CombineType.Join)
                                .withKeys(ID, USER_ID)
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_SIMPLE_CONDITION_WITH_DATE_DIFF_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlCareIndicatorNonPeriodSpecificCountOfCases() {
        SelectColumn selectBpFormCaseId = new SelectColumnBuilder()
                .withColumn(BP_FORM, CASE_ID)
                .build();
        SelectColumn selectCountBpFormCaseId = new SelectColumnBuilder()
                .withColumnAndFunction(BP_FORM, CASE_ID, SelectColumnFunctionType.Count)
                .build();

        DwQuery bpFormQuery = new DwQueryBuilder()
                .withSelectColumn(selectBpFormCaseId)
                .withTableName(BP_FORM)
                .withGroupBy(
                        new GroupByConditionBuilder()
                                .withField(BP_FORM, CASE_ID)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(selectCountBpFormCaseId)
                                                .withComparison(ComparisonType.GreaterEqual, ONE)
                                )
                )
                .build();

        DwQueryCombination bpFormJoin = new DwQueryCombinationBuilder()
                .withCombineType(CombineType.Join)
                .withDwQuery(bpFormQuery)
                .withKeys(CASE_ID, ID)
                .build();

        SelectColumn selectAll = new SelectColumnBuilder()
                .withColumn(WILDCARD)
                .build();

        DwQuery flwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(FLW)
                .build();

        DwQueryCombination flwJoin = new DwQueryCombinationBuilder()
                .withCombineType(CombineType.Join)
                .withDwQuery(flwQuery)
                .withKeys(ID, USER_ID)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumnAndFunction(WILDCARD, SelectColumnFunctionType.Count)
                )
                .withTableName(MOTHER_CASE)
                .withCombination(bpFormJoin)
                .withCombination(flwJoin)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withValueComparison(FLW, FLW_STATE,
                                                        ComparisonType.Equal, FLW_STATE_NAME)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_INDICATOR_NON_PERIOD_SPECIFIC_COUNT_OF_CASES_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlCareIndicatorCountOfCasesInAPeriodDdOffsetBased() {
        SelectColumn selectAll = new SelectColumnBuilder()
                .withColumn(WILDCARD)
                .build();
        SelectColumn selectCountAll = new SelectColumnBuilder()
                .withColumnAndFunction(WILDCARD, SelectColumnFunctionType.Count)
                .build();

        DwQuery flwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(FLW)
                .build();

        DwQueryCombination flwJoin = new DwQueryCombinationBuilder()
                .withCombineType(CombineType.Join)
                .withDwQuery(flwQuery)
                .withKeys(ID, USER_ID)
                .build();

        WhereConditionGroup whereConditionGroup = new WhereConditionGroupBuilder()
                .withGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withDateValueComparison(MOTHER_CASE, ADD, ComparisonType.GreaterEqual,
                                                        FIRST_DATE, DATE_OFFSET_TEN_DAYS)
                                )
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withDateValueComparison(MOTHER_CASE, ADD, ComparisonType.LessEqual,
                                                        SECOND_DATE, DATE_OFFSET_TEN_DAYS)
                                )
                )
                .withGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withDateValueComparison(MOTHER_CASE, EDD, ComparisonType.GreaterEqual,
                                                        FIRST_DATE, DATE_OFFSET_TEN_DAYS)
                                )
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withDateValueComparison(MOTHER_CASE, EDD, ComparisonType.LessEqual,
                                                        SECOND_DATE, DATE_OFFSET_TEN_DAYS)
                                )
                                .withJoinType(WhereConditionJoinType.OR)
                )
                .withCondition(
                        new WhereConditionBuilder()
                                .withValueComparison(FLW, FLW_STATE, ComparisonType.Equal, FLW_STATE_NAME)
                )
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectCountAll)
                .withTableName(MOTHER_CASE)
                .withCombination(flwJoin)
                .withWhereConditionGroup(whereConditionGroup)
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_INDICATOR_COUNT_OF_CASES_IN_A_PERIOD_DD_OFFSET_BASED, sqlString);
    }

    @Test
    public void testPostgreSqlDateRangeComparison() {
        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumnAndFunction(BP_FORM, IFA_TABLETS_ISSUED, SelectColumnFunctionType.Count)
                )
                .withTableName(BP_FORM)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withDateRangeComparison(BP_FORM, TIME_START, FIRST_DATE,
                                                        SECOND_DATE, DATE_OFFSET_TEN_DAYS)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_DATE_RANGE_COMPARISON_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlHavingCountWildcard() {
        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumnAndFunction(BP_FORM, IFA_TABLETS_ISSUED, SelectColumnFunctionType.Count)
                )
                .withTableName(BP_FORM)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withDateRangeComparison(BP_FORM, TIME_START, FIRST_DATE,
                                                        SECOND_DATE, DATE_OFFSET_TEN_DAYS)
                                )
                )
                .withGroupBy(
                        new GroupByConditionBuilder()
                                .withField(BP_FORM, IFA_TABLETS_ISSUED)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(
                                                        new SelectColumnBuilder()
                                                                .withColumnAndFunction(BP_FORM, WILDCARD,
                                                                        SelectColumnFunctionType.Count)
                                                )
                                                .withComparison(ComparisonType.GreaterEqual, ONE)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_HAVING_COUNT_WILDCARD_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlFieldComparison() {
        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumnAndFunction(BP_FORM, IFA_TABLETS_ISSUED, SelectColumnFunctionType.Count)
                )
                .withTableName(BP_FORM)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withFieldComparison(BP_FORM, IFA_TABLETS_ISSUED, "-1",
                                                        ComparisonType.Equal, BP_FORM, IFA_TABLETS_TOTAL, "1")
                                )
                )
                .withGroupBy(
                        new GroupByConditionBuilder()
                                .withField(BP_FORM, IFA_TABLETS_ISSUED)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(
                                                        new SelectColumnBuilder()
                                                                .withColumnAndFunction(BP_FORM, WILDCARD,
                                                                        SelectColumnFunctionType.Count)
                                                )
                                                .withComparison(ComparisonType.GreaterEqual, ONE)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_FIELD_COMPARISON_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlMultipleJoins() {
        SelectColumn selectAllMothers = new SelectColumnBuilder()
                .withColumn(MOTHER_CASE, ID)
                .build();
        SelectColumn selectAll = new SelectColumnBuilder()
                .withColumn(WILDCARD)
                .build();

        DwQueryCombination closeChildFormJoin = new DwQueryCombinationBuilder()
                .withCombineType(CombineType.Join)
                .withDwQuery(
                        new DwQueryBuilder()
                                .withSelectColumn(selectAll)
                                .withTableName(CLOSE_CHILD_FORM)
                )
                .withKeys(CASE_ID, ID)
                .build();

        DwQueryCombination closeMotherFormJoin = new DwQueryCombinationBuilder()
                .withCombineType(CombineType.Join)
                .withDwQuery(
                        new DwQueryBuilder()
                                .withSelectColumn(selectAll)
                                .withTableName(CLOSE_MOTHER_FORM)
                )
                .withKeys(CASE_ID, ID)
                .build();

        DwQuery childCaseQuery = new DwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(CHILD_CASE)
                .withCombination(closeChildFormJoin)
                .build();

        DwQueryCombination childCaseQueryCombination = new DwQueryCombinationBuilder()
                .withDwQuery(childCaseQuery)
                .withCombineType(CombineType.Join)
                .withKeys(MOTHER_ID, ID)
                .build();

        DwQuery bpFormQuery = new DwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(BP_FORM)
                .build();

        DwQueryCombination bpFormQueryCombination = new DwQueryCombinationBuilder()
                .withDwQuery(bpFormQuery)
                .withCombineType(CombineType.Join)
                .withKeys(CASE_ID, ID)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAllMothers)
                .withTableName(MOTHER_CASE)
                .withCombination(closeMotherFormJoin)
                .withCombination(childCaseQueryCombination)
                .withCombination(bpFormQueryCombination)
                .build();

        String sqlQuery = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlQuery);
        assertEquals(EXPECTED_POSTGRESQL_MULTIPLE_JOINS_SQL_STRING, sqlQuery);
    }

    @Test
    public void testPostgreSqlEnumRangeComparison() {
        SelectColumn selectColumn = new SelectColumnBuilder()
                .withColumn(MOTHER_CASE, AGE)
                .build();

        Set<String> values = new LinkedHashSet<>();
        values.add(AGE_1);
        values.add(AGE_2);
        values.add(AGE_3);

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectColumn)
                .withTableName(MOTHER_CASE)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withEnumRangeComparison(MOTHER_CASE, AGE, values)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_ENUM_RANGE_COMPARISON, sqlString);
    }

    @Test
    public void testPostgreSqlNvlWithoutAggregateFunction() {
        SelectColumn selectColumn = new SelectColumnBuilder()
                .withColumn(MOTHER_CASE, AGE)
                .withNullValue(ZERO)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectColumn)
                .withTableName(MOTHER_CASE)
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_NVL_WITHOUT_AGGREGATE_FUNCTION_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlNvlWithAggregateFunction() {
        SelectColumn selectColumn = new SelectColumnBuilder()
                .withColumnAndFunction(MOTHER_CASE, AGE, SelectColumnFunctionType.Count)
                .withNullValue(ZERO)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectColumn)
                .withTableName(MOTHER_CASE)
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_NVL_WITH_AGGREGATE_FUNCTION_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlComputedColumn() {
        SelectColumn selectAge = new SelectColumnBuilder()
                .withColumn(
                        new ComputedColumnBuilder()
                                .withComputedColumn(
                                        new ComputedColumnBuilder()
                                                .withComputedColumn(MOTHER_CASE, AGE)
                                                .withComputedColumn(OperatorType.Add, MOTHER_CASE, AGE)
                                )
                                .withComputedColumn(OperatorType.Multiply,
                                        new ComputedColumnBuilder()
                                                .withComputedColumn(MOTHER_CASE, AGE)
                                                .withComputedColumn(OperatorType.Subtract, MOTHER_CASE, AGE)
                                )
                                .withComputedColumn(OperatorType.Add, MOTHER_CASE, AGE)
                )
                .withAlias(AGE_COMPUTED_COLUMN_ALIAS)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAge)
                .withTableName(MOTHER_CASE)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withValueComparison(selectAge, ComparisonType.Greater, ONE)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_COMPUTED_COLUMN_WITH_CONDITION_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlComputedColumnWithFunctionAndNvl() {
        SelectColumn selectAge = new SelectColumnBuilder()
                .withColumn(
                        new ComputedColumnBuilder()
                                .withComputedColumn(
                                        new ComputedColumnBuilder()
                                                .withComputedColumn(MOTHER_CASE, AGE)
                                                .withComputedColumn(OperatorType.Add, MOTHER_CASE, AGE)
                                )
                                .withComputedColumn(OperatorType.Multiply,
                                        new ComputedColumnBuilder()
                                                .withComputedColumn(MOTHER_CASE, AGE)
                                                .withComputedColumn(OperatorType.Subtract, MOTHER_CASE, AGE)
                                )
                                .withComputedColumn(OperatorType.Add, MOTHER_CASE, AGE)
                )
                .withFunction(SelectColumnFunctionType.Max)
                .withAlias(AGE_COMPUTED_COLUMN_ALIAS)
                .withNullValue(ZERO)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAge)
                .withTableName(MOTHER_CASE)
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_COMPUTED_COLUMN_WITH_FUNCTIOM_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlValueToLowerCase() {
        SelectColumn selectCountFlw = new SelectColumnBuilder()
                .withColumnAndFunction(FLW, FLW_STATE, SelectColumnFunctionType.Count)
                .build();
        SelectColumn selectFlw = new SelectColumnBuilder()
                .withColumn(FLW, FLW_STATE)
                .withValueToLowerCase(true)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectCountFlw)
                .withTableName(FLW)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withValueComparison(selectFlw, ComparisonType.Equal, FLW_STATE_NAME)
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_VALUE_TO_LOWER_CASE_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlLimit() {
        SelectColumn selectAll = new SelectColumnBuilder()
                .withColumn(WILDCARD)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(MOTHER_CASE)
                .withLimit(Integer.parseInt(ONE))
                .build();
        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_LIMIT_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlLimitZero() {
        SelectColumn selectAll = new SelectColumnBuilder()
                .withColumn(WILDCARD)
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(MOTHER_CASE)
                .withLimit(Integer.parseInt(ZERO))
                .build();
        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_LIMIT_ZERO_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlOrderBy() {
        SelectColumn selectId = new SelectColumnBuilder()
                .withColumn(MOTHER_CASE, ID)
                .withNullValue("0")
                .build();
        SelectColumn selectAge = new SelectColumnBuilder()
                .withColumn(MOTHER_CASE, AGE)
                .withNullValue("0")
                .build();

        DwQuery dwQuery = new DwQueryBuilder()
                .withSelectColumn(selectId)
                .withSelectColumn(selectAge)
                .withTableName(MOTHER_CASE)
                .withOrderBy(new OrderBy(selectAge, OrderByType.Ascending))
                .withOrderBy(new OrderBy(selectId, OrderByType.Descending))
                .build();
        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_ORDER_BY_MULTIPLE_SQL_STRING, sqlString);
    }
}
