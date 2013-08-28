package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.DwQueryCombination;
import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.conditions.where.WhereConditionGroup;
import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.enums.ComparisonType;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;
import org.dwQueryBuilder.data.enums.WhereConditionJoinType;
import org.dwQueryBuilder.data.queries.ComplexDwQuery;
import org.dwQueryBuilder.data.queries.DwQuery;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;
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
    private static final String CF_MOTHER_FORM = "cf_mother_form";
    private static final String FLW = "flw";
    private static final String FLW_DISTRICT = "district";
    private static final String FLW_DISTRICT_NAME = "Bihar";
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

    private static final String EXPECTED_POSTGRESQL_COMPLEX_CONDITION_COMBINATION_SQL_STRING =
            "select \"report\".\"mother_case\".\"id\" from \"report\".\"mother_case\" join" +
                    " (select * from \"report\".\"close_mother_form\") as \"facts\" on" +
                    " \"report\".\"mother_case\".\"id\" = \"facts\".\"case_id\" join" +
                    " (select * from \"report\".\"child_case\" join" +
                    " (select * from \"report\".\"close_child_form\") as \"facts\" on" +
                    " \"report\".\"child_case\".\"id\" = \"facts\".\"case_id\")" +
                    " as \"child_case\" on \"child_case\".\"mother_id\" = \"report\".\"mother_case\".\"id\"";
    private static final String EXPECTED_POSTGRESQL_COMPLEX_CONDITION_SQL_STRING =
            "select * from \"report\".\"mother_case\" join ((select \"report\".\"bp_form\".\"case_id\" from " +
                    "\"report\".\"bp_form\" group by \"report\".\"bp_form\".\"case_id\" having " +
                    "count(\"bp_form\".\"case_id\") >= '2') union (select \"report\".\"cf_mother_form\".\"case_id\" " +
                    "from \"report\".\"cf_mother_form\" group by \"report\".\"cf_mother_form\".\"case_id\" " +
                    "having count(\"cf_mother_form\".\"case_id\") >= '3')) as \"facts\" on " +
                    "\"report\".\"mother_case\".\"id\" = \"facts\".\"case_id\" join " +
                    "(select * from \"report\".\"flw\") as \"flw\" on \"flw\".\"id\" = " +
                    "\"report\".\"mother_case\".\"user_id\" where \"flw\".\"district\" = 'Bihar'";
    private static final String EXPECTED_POSTGRESQL_SIMPLE_CONDITION_WITH_DATE_DIFF_SQL_STRING =
            "select count(\"report\".\"bp_form\".\"ifa_tablets_issued\") from \"report\".\"bp_form\" " +
                    "join (select * from \"report\".\"flw\") as \"flw\" on \"flw\".\"id\" = " +
                    "\"report\".\"bp_form\".\"user_id\" where (\"bp_form\".\"ifa_tablets_issued\" <= '120'" +
                    " or (\"bp_form\".\"time_end\" + '-120') - (\"bp_form\".\"time_start\" + '360') >= '500')";
    private static final String EXPECTED_POSTGRESQL_INDICATOR_NON_PERIOD_SPECIFIC_COUNT_OF_CASES_SQL_STRING =
            "select count(*) from \"report\".\"mother_case\" join (select \"report\".\"bp_form\".\"case_id\" from " +
                    "\"report\".\"bp_form\" group by \"report\".\"bp_form\".\"case_id\" having " +
                    "count(\"bp_form\".\"case_id\") >= '1') as \"facts\" on " +
                    "\"report\".\"mother_case\".\"id\" = \"facts\".\"case_id\" join " +
                    "(select * from \"report\".\"flw\") as \"flw\" on \"flw\".\"id\" = " +
                    "\"report\".\"mother_case\".\"user_id\" where \"flw\".\"district\" = 'Bihar'";
    private static final String EXPECTED_POSTGRESQL_INDICATOR_COUNT_OF_CASES_IN_A_PERIOD_DD_OFFSET_BASED =
            "select count(*) from \"report\".\"mother_case\" join (select * from \"report\".\"flw\") as \"flw\" on " +
                    "\"flw\".\"id\" = \"report\".\"mother_case\".\"user_id\" where ((((\"mother_case\".\"add\"" +
                    " + cast('+10 00:00:00.000000000' as interval day to second)) >= '02-02-2013' and " +
                    "(\"mother_case\".\"add\" + cast('+10 00:00:00.000000000' as interval day to second))" +
                    " <= '04-02-2013') or ((\"mother_case\".\"edd\" + cast('+10 00:00:00.000000000' " +
                    "as interval day to second)) >= '02-02-2013' and (\"mother_case\".\"edd\"" +
                    " + cast('+10 00:00:00.000000000' as interval day to second)) <= '04-02-2013'))" +
                    " and \"flw\".\"district\" = 'Bihar')";
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
                    "\"bp_form\".\"ifa_tablets_issued\" = \"bp_form\".\"ifa_tablets_total\" group by " +
                    "\"report\".\"bp_form\".\"ifa_tablets_issued\" having count(\"bp_form\".*) >= '1'";
    private static final String EXPECTED_POSTGRESQL_MULTIPLE_JOINS_SQL_STRING =
            "select \"report\".\"mother_case\".\"id\" from \"report\".\"mother_case\" join " +
                    "(select * from \"report\".\"close_mother_form\") as \"facts\" on " +
                    "\"report\".\"mother_case\".\"id\" = \"facts\".\"case_id\" join " +
                    "(select * from \"report\".\"child_case\" join (select * from \"report\".\"close_child_form\") " +
                    "as \"facts\" on \"report\".\"child_case\".\"id\" = \"facts\".\"case_id\") as " +
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

    @Test
    public void testPostgreSqlQueryBuilderSimpleConditionWithDateDiffComparison() {
        SimpleDwQuery flwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumn(WILDCARD)
                )
                .withTableName(FLW)
                .build();

        DwQuery dwQuery = new SimpleDwQueryBuilder()
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
    public void testPostgreSqlQueryBuilderComplexCondition() {
        SelectColumn bpCaseId = new SelectColumnBuilder()
                .withColumn(BP_FORM, CASE_ID).build();
        SelectColumn bpCaseIdCount = new SelectColumnBuilder()
                .withColumnAndFunction(BP_FORM, CASE_ID, SelectColumnFunctionType.Count).build();
        SelectColumn cfCaseId = new SelectColumnBuilder()
                .withColumn(CF_MOTHER_FORM, CASE_ID).build();
        SelectColumn cfCaseIdCount = new SelectColumnBuilder()
                .withColumnAndFunction(CF_MOTHER_FORM, CASE_ID, SelectColumnFunctionType.Count).build();

        SimpleDwQuery bpFormQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(bpCaseId)
                .withTableName(BP_FORM)
                .withGroupBy(
                        new GroupByConditionBuilder()
                                .withField(BP_FORM, CASE_ID)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(bpCaseIdCount)
                                                .withComparison(ComparisonType.GreaterEqual, "2")
                                )
                )
                .build();

        SimpleDwQuery cfMotherFormQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(cfCaseId)
                .withTableName(CF_MOTHER_FORM)
                .withGroupBy(
                        new GroupByConditionBuilder()
                                .withField(CF_MOTHER_FORM, CASE_ID)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(cfCaseIdCount)
                                                .withComparison(ComparisonType.GreaterEqual, "3")
                                )
                )
                .build();

        SimpleDwQuery flwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumn(WILDCARD)
                )
                .withTableName(FLW)
                .build();

        DwQuery dwQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                            .withColumn(WILDCARD)
                )
                .withDimension(MOTHER_CASE)
                .withFact(
                        new FactBuilder().withTable(bpFormQuery)
                )
                .withFact(
                        new FactBuilder().withTable(cfMotherFormQuery)
                                .withCombineType(CombineType.Union)
                )
                .withKeys(ID, CASE_ID)
                .withCombination(
                        new DwQueryCombinationBuilder()
                            .withDwQuery(flwQuery)
                            .withCombineType(CombineType.Join)
                            .withKeys(ID, USER_ID)
                )
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withValueComparison(
                                                        FLW,
                                                        FLW_DISTRICT,
                                                        ComparisonType.Equal,
                                                        FLW_DISTRICT_NAME
                                                )
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_COMPLEX_CONDITION_SQL_STRING, sqlString);
    }

    @Test
    public void testPostgreSqlCareIndicatorNonPeriodSpecificCountOfCases() {
        SelectColumn selectBpFormCaseId = new SelectColumnBuilder()
                .withColumn(BP_FORM, CASE_ID)
                .build();
        SelectColumn selectCountBpFormCaseId = new SelectColumnBuilder()
                .withColumnAndFunction(BP_FORM, CASE_ID, SelectColumnFunctionType.Count)
                .build();

        SimpleDwQuery flwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                            .withColumn(WILDCARD)
                )
                .withTableName(FLW)
                .build();

        DwQueryCombination flwJoin = new DwQueryCombinationBuilder()
                .withCombineType(CombineType.Join)
                .withDwQuery(flwQuery)
                .withKeys(ID, USER_ID)
                .build();

        SimpleDwQuery bpFormQuery = new SimpleDwQueryBuilder()
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

        DwQuery dwQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumnAndFunction(WILDCARD, SelectColumnFunctionType.Count)
                )
                .withDimension(MOTHER_CASE)
                .withFact(
                        new FactBuilder()
                                .withTable(bpFormQuery)
                )
                .withCombination(flwJoin)
                .withKeys(ID, CASE_ID)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withValueComparison(FLW, FLW_DISTRICT,
                                                        ComparisonType.Equal, FLW_DISTRICT_NAME)
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

        SimpleDwQuery flwQuery = new SimpleDwQueryBuilder()
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
                                .withValueComparison(FLW, FLW_DISTRICT, ComparisonType.Equal, FLW_DISTRICT_NAME)
                )
                .build();

        DwQuery dwQuery = new SimpleDwQueryBuilder()
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
    public void testPostgreSqlComplexDwQueryCombination() {
        SelectColumn selectAllMothers = new SelectColumnBuilder()
                .withColumn(MOTHER_CASE, ID)
                .build();
        SelectColumn selectAll = new SelectColumnBuilder()
                .withColumn(WILDCARD)
                .build();

        ComplexDwQuery childCaseQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(selectAll)
                .withDimension(CHILD_CASE)
                .withFact(
                        new FactBuilder()
                            .withTable(
                                    new SimpleDwQueryBuilder()
                                            .withSelectColumn(selectAll)
                                            .withTableName(CLOSE_CHILD_FORM)
                            )
                )
                .withKeys(ID, CASE_ID)
                .build();

        DwQueryCombination childCaseQueryCombination = new DwQueryCombinationBuilder()
                .withDwQuery(childCaseQuery)
                .withCombineType(CombineType.Join)
                .withKeys(MOTHER_ID, ID)
                .build();

        DwQuery dwQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(selectAllMothers)
                .withDimension(MOTHER_CASE)
                .withFact(
                        new FactBuilder()
                            .withTable(
                                    new SimpleDwQueryBuilder()
                                        .withSelectColumn(selectAll)
                                        .withTableName(CLOSE_MOTHER_FORM)
                            )
                )
                .withKeys(ID, CASE_ID)
                .withCombination(childCaseQueryCombination)
                .build();

        String sqlQuery = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlQuery);
        assertEquals(EXPECTED_POSTGRESQL_COMPLEX_CONDITION_COMBINATION_SQL_STRING, sqlQuery);
    }

    @Test
    public void testPostgreSqlDateRangeComparison() {
        DwQuery dwQuery = new SimpleDwQueryBuilder()
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
        DwQuery dwQuery = new SimpleDwQueryBuilder()
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
        DwQuery dwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withColumnAndFunction(BP_FORM, IFA_TABLETS_ISSUED, SelectColumnFunctionType.Count)
                )
                .withTableName(BP_FORM)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                                .withCondition(
                                        new WhereConditionBuilder()
                                                .withFieldComparison(BP_FORM, IFA_TABLETS_ISSUED,
                                                        ComparisonType.Equal, BP_FORM, IFA_TABLETS_TOTAL)
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

        ComplexDwQuery childCaseQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(selectAll)
                .withDimension(CHILD_CASE)
                .withFact(
                        new FactBuilder()
                                .withTable(
                                        new SimpleDwQueryBuilder()
                                                .withSelectColumn(selectAll)
                                                .withTableName(CLOSE_CHILD_FORM)
                                )
                )
                .withKeys(ID, CASE_ID)
                .build();

        DwQueryCombination childCaseQueryCombination = new DwQueryCombinationBuilder()
                .withDwQuery(childCaseQuery)
                .withCombineType(CombineType.Join)
                .withKeys(MOTHER_ID, ID)
                .build();

        SimpleDwQuery bpFormQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(selectAll)
                .withTableName(BP_FORM)
                .build();

        DwQueryCombination bpFormQueryCombination = new DwQueryCombinationBuilder()
                .withDwQuery(bpFormQuery)
                .withCombineType(CombineType.Join)
                .withKeys(CASE_ID, ID)
                .build();

        DwQuery dwQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(selectAllMothers)
                .withDimension(MOTHER_CASE)
                .withFact(
                        new FactBuilder()
                                .withTable(
                                        new SimpleDwQueryBuilder()
                                                .withSelectColumn(selectAll)
                                                .withTableName(CLOSE_MOTHER_FORM)
                                )
                )
                .withKeys(ID, CASE_ID)
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

        SimpleDwQuery dwQuery = new SimpleDwQueryBuilder()
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

        SimpleDwQuery dwQuery = new SimpleDwQueryBuilder()
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

        SimpleDwQuery dwQuery = new SimpleDwQueryBuilder()
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

        DwQuery dwQuery = new SimpleDwQueryBuilder()
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
                .withNullValue("0")
                .build();

        DwQuery dwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(selectAge)
                .withTableName(MOTHER_CASE)
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(EXPECTED_POSTGRESQL_COMPUTED_COLUMN_WITH_FUNCTIOM_SQL_STRING, sqlString);
    }
}
