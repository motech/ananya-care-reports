package org.motechproject.carereporting.indicator.query;

import org.apache.commons.lang.StringUtils;
import org.jooq.AggregateFunction;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.impl.DSL;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.types.FieldType;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.service.FormsService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.jooq.impl.DSL.avg;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.currentDate;
import static org.jooq.impl.DSL.dateDiff;
import static org.jooq.impl.DSL.fieldByName;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.tableByName;
import static org.jooq.impl.DSL.val;

public class CalculatorQueryBuilder {

    private static final String UNCHECKED = "unchecked";
    private static final String FLW = "flw";
    private static final String SCHEMA_NAME = "report";
    private static final String CHILD_CASE = "child_case";
    private static final String CHILD_ALIAS = "child";
    private static final String MOTHER_CASE = "mother_case";
    private static final String MOTHER_ALIAS = "mother";
    private static final Integer SECONDS_TO_DAYS = 3600 * 24;

    public enum OperationType {
        Undefined,
        Average,
        Count,
        Sum
    }

    private FormsService formsService;
    private IndicatorEntity indicator;
    private ComplexConditionEntity complexCondition;
    private ComputedFieldEntity computedField;
    private OperationType operation;
    private AreaEntity area;
    private Integer frequency;
    private Map<String, String> tableNameToCaseName = new LinkedHashMap<>();
    private DSLContext create;
    private SelectQuery selectQuery;

    public CalculatorQueryBuilder(DataSource dataSource, FormsService formsService) {
        this.formsService = formsService;
        this.indicator = null;
        this.complexCondition = null;
        this.computedField = null;
        this.operation = OperationType.Undefined;
        this.area = null;
        this.frequency = null;
        this.selectQuery = null;
        Settings settings = new Settings().withRenderFormatted(true)
                .withStatementType(StatementType.STATIC_STATEMENT);
        this.create = DSL.using(dataSource, SQLDialect.POSTGRES, settings);
    }

    public CalculatorQueryBuilder reset() {
        this.indicator = null;
        this.computedField = null;
        this.complexCondition = null;
        this.operation = OperationType.Undefined;
        this.area = null;
        this.frequency = null;
        this.selectQuery = null;

        return this;
    }

    public CalculatorQueryBuilder withIndicator(IndicatorEntity indicator) {
        this.indicator = indicator;
        return this;
    }

    public CalculatorQueryBuilder withComplexCondition(ComplexConditionEntity complexCondition) {
        this.complexCondition = complexCondition;
        return this;
    }

    public CalculatorQueryBuilder withOperation(OperationType operation) {
        this.operation = operation;
        return this;
    }

    public CalculatorQueryBuilder withArea(AreaEntity area) {
        this.area = area;
        return this;
    }

    public CalculatorQueryBuilder withFrequency(Integer frequency) {
        this.frequency = frequency;
        return this;
    }

    @SuppressWarnings(UNCHECKED)
    public SelectQuery build() {
        validateBuilder();
        resolveConditions();

        AggregateFunction operationFunction = null;
        switch (operation) {
            case Average:
                if (computedField.getType().equals(FieldType.Number)) {
                    operationFunction = avg(fieldByName(
                            BigDecimal.class,
                            computedField.getForm().getTableName(),
                            computedField.getName()));
                }
                break;
            case Count:
                operationFunction = count();
                break;
            case Sum:
                if (computedField.getType().equals(FieldType.Number)) {
                    operationFunction = sum(fieldByName(
                            BigDecimal.class,
                            computedField.getForm().getTableName(),
                            computedField.getName()));
                }
                break;
            case Undefined:
                break;
            default:
                break;
        }

        String caseTableName = formsService.getForeignKeyForTable(computedField.getForm().getTableName());
        String caseAlias = (caseTableName.equals(CHILD_CASE)) ? CHILD_ALIAS : MOTHER_ALIAS;

        SelectJoinStep selectJoinStep = create.select(operationFunction)
                .from(tableByName(SCHEMA_NAME, caseTableName).as(caseAlias));
        selectJoinStep = buildJoins(caseTableName, selectJoinStep);
        SelectConditionStep selectConditionStep = buildWhereClause(selectJoinStep);

        selectQuery = selectConditionStep.getQuery();
        return selectQuery;
    }

    @SuppressWarnings(UNCHECKED)
    private SelectJoinStep buildJoins(String caseTableName, SelectJoinStep selectJoinStep) {
        SelectJoinStep step = selectJoinStep;
        Table motherCase = tableByName(SCHEMA_NAME, MOTHER_CASE).as(MOTHER_ALIAS);
        Table flwTable = tableByName(SCHEMA_NAME, FLW).as(FLW);
        Table computedFieldTable = tableByName(SCHEMA_NAME, computedField.getForm().getTableName())
                .as(computedField.getForm().getTableName());
        String caseAlias = (caseTableName.equals(CHILD_CASE)) ? CHILD_ALIAS : MOTHER_ALIAS;

        if (caseTableName.equals(CHILD_CASE)) {
            step = step.join(motherCase).on(fieldByName(MOTHER_ALIAS, "id")
                    .equal(fieldByName(CHILD_ALIAS, "mother_id")));
        }

        if (StringUtils.isNotEmpty(area.getName())) {
            step = step.join(flwTable).on(fieldByName(FLW, "id")
                    .equal(fieldByName(MOTHER_ALIAS, "user_id")));
        }

        step = step.join(computedFieldTable).on(
                fieldByName(computedFieldTable.getName(), "case_id")
                        .equal(fieldByName(caseAlias, "id")));

    //    if (complexCondition == null || complexCondition.getConditions().isEmpty()) {
      //      return step;
        //}

        for (Map.Entry<String, String> entry : tableNameToCaseName.entrySet()) {
            if (entry.getKey().equals(computedField.getForm().getTableName())) {
                continue;
            }

            Table keyTable = tableByName(SCHEMA_NAME, entry.getKey()).as(entry.getKey());

            step = step.join(keyTable).on(
                    fieldByName(keyTable.getName(), "case_id")
                            .equal(fieldByName(caseAlias, "id")));
        }

        return step;
    }

    @SuppressWarnings(UNCHECKED)
    private SelectConditionStep buildWhereClause(SelectJoinStep selectJoinStep) {
        SelectConditionStep step = selectJoinStep.where();

        Table flwTable = tableByName(SCHEMA_NAME, FLW).as(FLW);
        Field flwLevelField = fieldByName(flwTable.getName(), area.getLevel().getName());
        Table computedFieldTable = tableByName(SCHEMA_NAME, computedField.getForm().getTableName())
                .as(computedField.getForm().getTableName());
        Field computedFieldTimeEndField = fieldByName(computedFieldTable.getName(), "time_end");

        if (area != null) {
            step = step.and(flwLevelField.equal(area.getName()));
        }

        if (frequency != null && frequency > 0) {
            Integer daysAsSeconds = frequency * SECONDS_TO_DAYS;
            Object days = val(daysAsSeconds.toString());
            Field diff = dateDiff(currentDate(), computedFieldTimeEndField);
            step = step.and(diff.lessThan(days));
        }

        if (complexCondition == null) {
            return step;
        }

        return buildWhereForConditions(step);
    }

    @SuppressWarnings(UNCHECKED)
    private SelectConditionStep buildWhereForConditions(SelectConditionStep selectConditionStep) {
        SelectConditionStep step = selectConditionStep;

        /*
        for (ConditionEntity conditionEntity : complexCondition.getConditions()) {
            ComputedFieldEntity field1 = conditionEntity.getField1();
            ComputedFieldEntity field2;
            String operator = conditionEntity.getComparisonSymbol().getName();

            Table field1Table = tableByName(SCHEMA_NAME, field1.getForm().getTableName())
                    .as(field1.getForm().getTableName());

            if (conditionEntity instanceof DateDiffComparisonConditionEntity) {
                DateDiffComparisonConditionEntity condition = (DateDiffComparisonConditionEntity) conditionEntity;
                field2 = condition.getField2();
                Integer value = condition.getValue();
                Table field2Table = tableByName(SCHEMA_NAME, field2.getForm().getTableName())
                        .as(field2.getForm().getTableName());

                Field diff = dateDiff(
                        fieldByName(Date.class, field1Table.getName(), field1.getName()),
                        fieldByName(Date.class, field2Table.getName(), field2.getName()));
                step = step.and(getConditionByOperator(diff, value, operator));

            } else if (conditionEntity instanceof FieldComparisonConditionEntity) {
                FieldComparisonConditionEntity condition = (FieldComparisonConditionEntity) conditionEntity;
                field2 = condition.getField2();
                Table field2Table = tableByName(SCHEMA_NAME, field2.getForm().getTableName())
                        .as(field2.getForm().getTableName());

                step = step.and(getConditionByOperator(
                        fieldByName(field1Table.getName(), field1.getName()),
                        fieldByName(field2Table.getName(), field2.getName()),
                        operator));

            } else if (conditionEntity instanceof ValueComparisonConditionEntity) {
                ValueComparisonConditionEntity condition = (ValueComparisonConditionEntity) conditionEntity;
                String value = condition.getValue();

                step = step.and(getConditionByOperator(
                        fieldByName(field1Table.getName(), field1.getName()),
                        value,
                        operator));
            }
        }
        */

        return step;
    }

    private void validateBuilder() {
        if (indicator == null) {
            throw new CareRuntimeException("Indicator must not be null.");
        } else if (operation == OperationType.Undefined) {
            throw new CareRuntimeException("Operation type must be defined.");
        //} else if (complexCondition != null && (complexCondition.getConditions() == null
          //      || complexCondition.getConditions().size() <= 0)) {
            //throw new CareRuntimeException("Complex condition must have at least one condition.");
        } else if (computedField == null) {
            throw new CareRuntimeException("Computed field must not be null.");
        }
    }

    private void resolveConditions() {
        if (complexCondition == null) {
            return;
        }

        /*
        for (ConditionEntity conditionEntity : complexCondition.getConditions()) {
            ComputedFieldEntity field2 = null;
            String field1ForeignKey = formsService.getForeignKeyForTable(
                    conditionEntity.getField1().getForm().getTableName());
            String field2ForeignKey = null;
            String field1TableName = conditionEntity.getField1().getForm().getTableName();
            String field2TableName = null;

            if (conditionEntity instanceof DateDiffComparisonConditionEntity) {
                DateDiffComparisonConditionEntity fieldComparison = (DateDiffComparisonConditionEntity) conditionEntity;
                field2 = fieldComparison.getField2();
            } else if (conditionEntity instanceof FieldComparisonConditionEntity) {
                FieldComparisonConditionEntity fieldComparison = (FieldComparisonConditionEntity) conditionEntity;
                field2 = fieldComparison.getField2();
            }

            if (field2 != null) {
                field2TableName = field2.getForm().getTableName();
                field2ForeignKey = formsService.getForeignKeyForTable(field2TableName);
            }

            addTableNameToCaseEntry(field1TableName, field1ForeignKey, field2TableName, field2ForeignKey);
        }
        */
    }

}
