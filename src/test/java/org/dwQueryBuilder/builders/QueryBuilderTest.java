package org.dwQueryBuilder.builders;

import org.dwQueryBuilder.data.SelectColumn;
import org.dwQueryBuilder.data.enums.CombineType;
import org.dwQueryBuilder.data.enums.OperatorType;
import org.dwQueryBuilder.data.enums.SelectColumnFunctionType;
import org.dwQueryBuilder.data.queries.DwQuery;
import org.dwQueryBuilder.data.queries.SimpleDwQuery;
import org.jooq.SQLDialect;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class QueryBuilderTest {

    private static final String WILDCARD = "*";
    private static final String TEST_SCHEMA_NAME = "report";
    private static final String BP_FORM = "bp_form";
    private static final String IFA_TABLETS_ISSUED = "ifa_tablets_issued";
    private static final String MOTHER_CASE = "mother_case";
    private static final String USER_ID = "user_id";
    private static final String ID = "id";
    private static final String CASE_ID = "case_id";
    private static final String CF_MOTHER_FORM = "cf_mother_form";
    private static final String FLW = "flw";
    private static final String FLW_DISTRICT = "district";
    private static final String FLW_DISTRICT_NAME = "Bihar";

    private static final String EXPECTED_POSTGRESQL_SIMPLE_CONDITION_SQL_STRING =
            "select count(\"report\".\"bp_form\".\"ifa_tablets_issued\"), " +
                    "\"report\".\"bp_form\".\"ifa_tablets_issued\" from \"report\".\"bp_form\" join " +
                    "(select * from \"report\".\"flw\") as \"flw\" on \"flw\".\"id\" = " +
                    "\"report\".\"bp_form\".\"user_id\" where (\"bp_form\".\"ifa_tablets_issued\" <= '120' " +
                    "and \"bp_form\".\"ifa_tablets_issued\" >= '90') group by " +
                    "\"report\".\"bp_form\".\"ifa_tablets_issued\" having " +
                    "\"report\".\"bp_form\".\"ifa_tablets_issued\" <> '100'";
    private static final String EXPECTED_POSTGRESQL_COMPLEX_CONDITION_SQL_STRING =
            "select * from \"report\".\"mother_case\" join ((select \"report\".\"bp_form\".\"case_id\" " +
                    "from \"report\".\"bp_form\" group by \"report\".\"bp_form\".\"case_id\" " +
                    "having count(\"report\".\"bp_form\".\"case_id\") >= '2') union " +
                    "(select \"report\".\"cf_mother_form\".\"case_id\" from \"report\".\"cf_mother_form\" " +
                    "group by \"report\".\"cf_mother_form\".\"case_id\" having " +
                    "count(\"report\".\"cf_mother_form\".\"case_id\") >= '3')) as \"facts\" " +
                    "on \"report\".\"mother_case\".\"id\" = \"facts\".\"case_id\" join " +
                    "(select * from \"report\".\"flw\") as \"flw\" on \"flw\".\"id\" = " +
                    "\"report\".\"mother_case\".\"user_id\" where \"flw\".\"district\" = 'Bihar'";

    @Test
    public void testPostgreSQLQueryBuilderSimpleCondition() {
        SimpleDwQuery flwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                            .withField(null, WILDCARD)
                )
                .withTableName(FLW)
                .build();

        DwQuery dwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder().withField(BP_FORM, IFA_TABLETS_ISSUED)
                                .withFunction(SelectColumnFunctionType.Count))
                .withSelectColumn(
                        new SelectColumnBuilder().withField(BP_FORM, IFA_TABLETS_ISSUED))
                .withGroupBy(
                        new GroupByConditionBuilder().withField(BP_FORM, IFA_TABLETS_ISSUED)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(
                                                        new SelectColumnBuilder()
                                                                .withField(BP_FORM, IFA_TABLETS_ISSUED))
                                                .withComparison(OperatorType.NotEqual, "100")
                                ))
                .withTableName(BP_FORM)
                .withWhereConditionGroup(
                        new WhereConditionGroupBuilder()
                            .withCondition(new WhereConditionBuilder()
                                    .withValueComparison(BP_FORM, IFA_TABLETS_ISSUED,
                                            OperatorType.LessEqual, "120"))
                            .withCondition(
                                    new WhereConditionBuilder()
                                            .withValueComparison(BP_FORM, IFA_TABLETS_ISSUED,
                                                    OperatorType.GreaterEqual, "90"))
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
        assertEquals(sqlString, EXPECTED_POSTGRESQL_SIMPLE_CONDITION_SQL_STRING);
    }

    @Test
    public void testPostgreSQLQueryBuilderComplexCondition() {
        SelectColumn bpCaseId = new SelectColumnBuilder()
                .withField(BP_FORM, CASE_ID).build();
        SelectColumn bpCaseIdCount = new SelectColumnBuilder()
                .withFieldAndFunction(BP_FORM, CASE_ID, SelectColumnFunctionType.Count).build();
        SelectColumn cfCaseId = new SelectColumnBuilder()
                .withField(CF_MOTHER_FORM, CASE_ID).build();
        SelectColumn cfCaseIdCount = new SelectColumnBuilder()
                .withFieldAndFunction(CF_MOTHER_FORM, CASE_ID, SelectColumnFunctionType.Count).build();

        SimpleDwQuery bpFormQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(bpCaseId)
                .withTableName(BP_FORM)
                .withGroupBy(
                        new GroupByConditionBuilder()
                                .withField(BP_FORM, CASE_ID)
                                .withHaving(
                                        new HavingConditionBuilder()
                                                .withSelectColumn(bpCaseIdCount)
                                                .withComparison(OperatorType.GreaterEqual, "2")
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
                                                .withComparison(OperatorType.GreaterEqual, "3")
                                )
                )
                .build();

        SimpleDwQuery flwQuery = new SimpleDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                                .withField(null, WILDCARD)
                )
                .withTableName(FLW)
                .build();

        DwQuery dwQuery = new ComplexDwQueryBuilder()
                .withSelectColumn(
                        new SelectColumnBuilder()
                            .withField(null, WILDCARD)
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
                                                        OperatorType.Equal,
                                                        FLW_DISTRICT_NAME
                                                )
                                )
                )
                .build();

        String sqlString = QueryBuilder.getDwQueryAsSQLString(SQLDialect.POSTGRES,
                TEST_SCHEMA_NAME, dwQuery, false);

        assertNotNull(sqlString);
        assertEquals(sqlString, EXPECTED_POSTGRESQL_COMPLEX_CONDITION_SQL_STRING);
    }
}
