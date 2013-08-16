package org.motechproject.carereporting.xml;

import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.dao.FrequencyDao;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.dao.ReportTypeDao;
import org.motechproject.carereporting.dao.RoleDao;
import org.motechproject.carereporting.dao.UserDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexDwQueryEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.DateDiffComparisonConditionEntity;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.FactEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.GroupedByEntity;
import org.motechproject.carereporting.domain.HavingEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.PeriodConditionEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.RoleEntity;
import org.motechproject.carereporting.domain.SelectColumnEntity;
import org.motechproject.carereporting.domain.SimpleDwQueryEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.motechproject.carereporting.domain.WhereGroupEntity;
import org.motechproject.carereporting.xml.mapping.Category;
import org.motechproject.carereporting.xml.mapping.Denominator;
import org.motechproject.carereporting.xml.mapping.DwQuery;
import org.motechproject.carereporting.xml.mapping.Fact;
import org.motechproject.carereporting.xml.mapping.Indicator;
import org.motechproject.carereporting.xml.mapping.Numerator;
import org.motechproject.carereporting.xml.mapping.Report;
import org.motechproject.carereporting.xml.mapping.Role;
import org.motechproject.carereporting.xml.mapping.SelectColumn;
import org.motechproject.carereporting.xml.mapping.User;
import org.motechproject.carereporting.xml.mapping.WhereCondition;
import org.motechproject.carereporting.xml.mapping.WhereGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

@Component
@Transactional(readOnly = true)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class XmlIndicatorParser {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private IndicatorCategoryDao indicatorCategoryDao;

    @Autowired
    private ComparisonSymbolDao comparisonSymbolDao;

    @Autowired
    private ComputedFieldDao computedFieldDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private FrequencyDao frequencyDao;

    @Autowired
    private ReportTypeDao reportTypeDao;

    @Transactional
    public IndicatorEntity parse(InputStream is) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Indicator.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Indicator indicator = (Indicator) unmarshaller.unmarshal(is);
        return createIndicatorEntityFromXmlIndicator(indicator);
    }

    private IndicatorEntity createIndicatorEntityFromXmlIndicator(Indicator indicator) {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setName(indicator.getName());
        if (indicator.getUser() != null) {
            indicatorEntity.setOwner(prepareOwner(indicator.getUser()));
        }
        indicatorEntity.setRoles(prepareRoles(indicator.getRoles()));
        indicatorEntity.setArea(findAreaByNameAndLevelName(indicator.getArea().getName(), indicator.getArea().getLevel().toString()));
        indicatorEntity.setCategories(prepareIndicatorCategories(indicator.getCategories()));
        indicatorEntity.setDefaultFrequency(findFrequencyById(indicator.getDefaultFrequency().getValue()));
        indicatorEntity.setNumerator(prepareNumerator(indicator.getNumerator()));
        indicatorEntity.setTrend(indicator.getTrend());
        indicatorEntity.setReports(prepareReports(indicator.getReports()));
        for (ReportEntity report: indicatorEntity.getReports()) {
            report.setIndicator(indicatorEntity);
        }
        if (indicator.getDenominator() != null) {
            indicatorEntity.setDenominator(prepareDenominator(indicator.getDenominator()));
        }
        return indicatorEntity;
    }

    private FrequencyEntity findFrequencyById(int id) {
        return frequencyDao.getById(id);
    }

    private AreaEntity findAreaByNameAndLevelName(String name, String levelName) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", name);
        LevelEntity level = levelDao.getByField("name", levelName.toLowerCase());
        fields.put("level", level);
        return areaDao.getByFields(fields);
    }

    private UserEntity prepareOwner(User user) {
        return userDao.getByField("username", user.getLogin());
    }

    private Set<RoleEntity> prepareRoles(List<Role> roles) {
        Set<RoleEntity> roleEntities = new HashSet<>();
        for (Role role: roles) {
            roleEntities.add(roleDao.getByField("name", role.getName()));
        }
        return roleEntities;
    }

    private Set<IndicatorCategoryEntity> prepareIndicatorCategories(List<Category> categories) {
        Set<IndicatorCategoryEntity> indicatorCategories = new HashSet<>();
        for (Category category: categories) {
            IndicatorCategoryEntity indicatorCategory = indicatorCategoryDao.getByField("name", category.getName());
            indicatorCategories.add(indicatorCategory);
        }
        return indicatorCategories;
    }

    private DwQueryEntity prepareDenominator(Denominator denominator) {
        if (denominator.getIndicatorId() != null) {
            return indicatorDao.getByIdWithFields(denominator.getIndicatorId(), "numerator").getNumerator();
        } else {
            return prepareDwQuery(denominator.getDwQuery());
        }
    }

    private DwQueryEntity prepareNumerator(Numerator numerator) {
        if (numerator.getIndicatorId() != null) {
            return indicatorDao.getByIdWithFields(numerator.getIndicatorId(), "numerator").getNumerator();
        } else {
            return prepareDwQuery(numerator.getDwQuery());
        }
    }

    private Set<ReportEntity> prepareReports(List<Report> reports) {
        Set<ReportEntity> reportEntities = new HashSet<>();
        if (reports != null) {
            for (Report report: reports) {
                reportEntities.add(prepareReport(report));
            }
        }
        return reportEntities;
    }

    private ReportEntity prepareReport(Report report) {
        ReportEntity reportEntity = new ReportEntity();
        reportEntity.setReportType(reportTypeDao.getByField("name", report.getType()));
        reportEntity.setLabelX(report.getLabelX());
        reportEntity.setLabelY(report.getLabelY());
        return reportEntity;
    }

    private Set<ConditionEntity> prepareWhereConditions(List<WhereCondition> whereConditions) {
        Set<ConditionEntity> conditions = new HashSet<>();
        for (WhereCondition condition: whereConditions) {
            ConditionEntity conditionEntity = createCondition(condition);
            conditions.add(conditionEntity);
        }
        return conditions;
    }

    private ConditionEntity createCondition(WhereCondition condition) {
        ConditionEntity conditionEntity = null;
        switch (condition.getType()) {
            case VALUE_COMPARISON:
                conditionEntity = createValueComparisonCondition(condition);
                break;
            case DATE_DIFF:
                conditionEntity = createDateDiffComparisonCondition(condition);
                break;
            case FIELD_COMPARISON:
                conditionEntity = createFieldComparisonCondition(condition);
                break;
            case PERIOD:
                conditionEntity = createPeriodCondition(condition);
                break;
            case DATE_WITH_OFFSET_DIFF:
            case DATE_RANGE:
            case ENUM_RANGE:
            default:
        }
        conditionEntity.setComparisonSymbol(comparisonSymbolDao.getByField("name", condition.getOperator()));
        conditionEntity.setField1(computedFieldDao.getByField("name", condition.getField()));
        return conditionEntity;
    }

    private ConditionEntity createValueComparisonCondition(WhereCondition condition) {
        ValueComparisonConditionEntity conditionEntity = new ValueComparisonConditionEntity();
        conditionEntity.setValue(condition.getValue());
        return conditionEntity;
    }

    private ConditionEntity createDateDiffComparisonCondition(WhereCondition condition) {
        DateDiffComparisonConditionEntity dateDiffComparisonConditionEntity = new DateDiffComparisonConditionEntity();
        dateDiffComparisonConditionEntity.setField2(computedFieldDao.getByField("name", condition.getSecondField()));
        dateDiffComparisonConditionEntity.setValue(Integer.valueOf(condition.getValue()));
        return dateDiffComparisonConditionEntity;
    }

    private ConditionEntity createFieldComparisonCondition(WhereCondition condition) {
        FieldComparisonConditionEntity fieldComparisonConditionEntity = new FieldComparisonConditionEntity();
        fieldComparisonConditionEntity.setField2(computedFieldDao.getByField("name", condition.getSecondField()));
        return fieldComparisonConditionEntity;
    }

    private ConditionEntity createPeriodCondition(WhereCondition condition) {
        PeriodConditionEntity periodConditionEntity = new PeriodConditionEntity();
        periodConditionEntity.setColumnName(condition.getColumnName());
        periodConditionEntity.setOffset(condition.getOffset());
        periodConditionEntity.setTableName(condition.getTableName());
        return periodConditionEntity;
    }

    private DwQueryEntity prepareDwQuery(DwQuery dwQuery) {
        ComplexDwQueryEntity dwQueryEntity = new ComplexDwQueryEntity();
        dwQueryEntity.setDimension(dwQuery.getDimension().getName());
        dwQueryEntity.setFacts(prepareFacts(dwQuery.getFacts()));
        if (dwQuery.getWhereGroup() != null) {
            WhereGroupEntity whereGroup = prepareWhereGroup(dwQuery.getWhereGroup());
            dwQueryEntity.setWhereGroup(whereGroup);
            dwQueryEntity.setHasPeriodCondition(hasPeriodCondition(whereGroup));
        }
        dwQueryEntity.setSelectColumns(new HashSet<SelectColumnEntity>());
        for (SelectColumn selectColumn: dwQuery.getSelectColumns()) {
            dwQueryEntity.getSelectColumns().add(prepareSelectColumn(selectColumn));
        }
        dwQueryEntity.setDimensionKey(dwQuery.getDimensionKey());
        dwQueryEntity.setFactKey(dwQuery.getFactKey());
        return dwQueryEntity;
    }

    private boolean hasPeriodCondition(WhereGroupEntity whereGroup) {
        for (ConditionEntity condition: whereGroup.getConditions()) {
            if (condition instanceof PeriodConditionEntity) {
                return true;
            }
        }
        for (WhereGroupEntity whereGroupEntity: whereGroup.getWhereGroups()) {
            if (hasPeriodCondition(whereGroupEntity)) {
                return true;
            }
        }
        return false;
    }

    private Set<FactEntity> prepareFacts(List<Fact> facts) {
        Set<FactEntity> factEntities = new HashSet<>();
        for (Fact fact: facts) {
            factEntities.add(prepareFact(fact));
        }
        return factEntities;
    }

    private SelectColumnEntity prepareSelectColumn(SelectColumn selectColumn) {
        SelectColumnEntity selectColumnEntity = new SelectColumnEntity();
        selectColumnEntity.setFunctionName(selectColumn.getAggregation());
        selectColumnEntity.setName(selectColumn.getFieldName());
        return selectColumnEntity;
    }

    private FactEntity prepareFact(Fact fact) {
        FactEntity factEntity = new FactEntity();
        factEntity.setCombineType(null);
        factEntity.setTable(prepareFactDwQuery(fact));
        return factEntity;
    }

    private SimpleDwQueryEntity prepareFactDwQuery(Fact fact) {
        SimpleDwQueryEntity simpleDwQueryEntity = new SimpleDwQueryEntity();
        simpleDwQueryEntity.setTableName(fact.getName());
        if (fact.getWhereGroup() != null) {
            WhereGroupEntity whereGroup = prepareWhereGroup(fact.getWhereGroup());
            simpleDwQueryEntity.setWhereGroup(whereGroup);
            simpleDwQueryEntity.setHasPeriodCondition(hasPeriodCondition(whereGroup));
        }
        simpleDwQueryEntity.setSelectColumns(new HashSet<SelectColumnEntity>());
        for (SelectColumn selectColumn: fact.getSelectColumns()) {
            SelectColumnEntity col = prepareSelectColumn(selectColumn);
            if (StringUtils.isEmpty(col.getTableName())) {
                col.setTableName(fact.getName());
            }
            simpleDwQueryEntity.getSelectColumns().add(col);
        }
        simpleDwQueryEntity.setGroupedBy(prepareGroupedBy(fact));
        return simpleDwQueryEntity;
    }

    private WhereGroupEntity prepareWhereGroup(WhereGroup whereGroup) {
        WhereGroupEntity whereGroupEntity = new WhereGroupEntity();
        if (whereGroup.getConditions() != null) {
            whereGroupEntity.setConditions(prepareWhereConditions(whereGroup.getConditions()));
        }
        if (whereGroup.getGroups() != null) {
            whereGroupEntity.setWhereGroups(prepareWhereGroups(whereGroup.getGroups()));
        }
        whereGroupEntity.setOperator(whereGroup.getJoin());
        return whereGroupEntity;
    }

    private Set<WhereGroupEntity> prepareWhereGroups(List<WhereGroup> whereGroups) {
        Set<WhereGroupEntity> whereGroupEntities = new HashSet<>();
        for (WhereGroup whereGroup: whereGroups) {
            whereGroupEntities.add(prepareWhereGroup(whereGroup));
        }
        return whereGroupEntities;
    }

    private GroupedByEntity prepareGroupedBy(Fact fact) {
        GroupedByEntity groupedByEntity = new GroupedByEntity();
        groupedByEntity.setFieldName(fact.getGroupedBy().getGroupBy());
        groupedByEntity.setHaving(prepareHaving(fact));
        groupedByEntity.setTableName(fact.getName());
        return groupedByEntity;
    }

    private HavingEntity prepareHaving(Fact fact) {
        HavingEntity having = new HavingEntity();
        having.setOperator(fact.getGroupedBy().getComparisonSymbol());
        having.setValue(fact.getGroupedBy().getValue());
        SelectColumnEntity selectCol = new SelectColumnEntity();
        selectCol.setFunctionName("Count");
        selectCol.setName("*");
        selectCol.setTableName(fact.getName());
        having.setSelectColumnEntity(selectCol);
        return having;
    }

}
