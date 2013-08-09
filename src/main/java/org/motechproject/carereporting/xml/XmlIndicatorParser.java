package org.motechproject.carereporting.xml;

import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexDwQueryEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.DateDiffComparisonConditionEntity;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.FactEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;
import org.motechproject.carereporting.domain.GroupedByEntity;
import org.motechproject.carereporting.domain.HavingEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.SimpleDwQueryEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.motechproject.carereporting.domain.WhereGroupEntity;
import org.motechproject.carereporting.xml.mapping.Category;
import org.motechproject.carereporting.xml.mapping.Denominator;
import org.motechproject.carereporting.xml.mapping.DwQuery;
import org.motechproject.carereporting.xml.mapping.Fact;
import org.motechproject.carereporting.xml.mapping.Indicator;
import org.motechproject.carereporting.xml.mapping.Numerator;
import org.motechproject.carereporting.xml.mapping.WhereCondition;
import org.motechproject.carereporting.xml.mapping.WhereGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        indicatorEntity.setArea(findAreaByNameAndLevelName(indicator.getArea().getName(), indicator.getArea().getLevel().toString()));
        indicatorEntity.setCategories(prepareIndicatorCategories(indicator.getCategories()));
        indicatorEntity.setDefaultFrequency(indicator.getDefaultFrequency().getValue());
        indicatorEntity.setDenominator(prepareDenominator(indicator.getDenominator()));
        indicatorEntity.setTrend(indicator.getTrend());
        if (indicator.getNumerator() != null) {
            indicatorEntity.setNumerator(prepareNumerator(indicator.getNumerator()));
        }
        return indicatorEntity;
    }

    private AreaEntity findAreaByNameAndLevelName(String name, String levelName) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", name);
        LevelEntity level = levelDao.getByField("name", levelName);
        fields.put("level", level);
        return areaDao.getByFields(fields);
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
            return indicatorDao.getByIdWithFields(denominator.getIndicatorId(), "denominator").getDenominator();
        } else {
            return prepareDwQuery(denominator.getDwQuery());
        }
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
            case DATE_DIFF:
                conditionEntity = createDateDiffComparisonCondition(condition);
            case FIELD_COMPARISON:
                conditionEntity = createFieldComparisonCondition(condition);
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

    private DwQueryEntity prepareNumerator(Numerator numerator) {
        if (numerator.getIndicatorId() != null) {
            return indicatorDao.getByIdWithFields(numerator.getIndicatorId(), "denominator").getDenominator();
        } else {
            return prepareDwQuery(numerator.getDwQuery());
        }
    }

    private DwQueryEntity prepareDwQuery(DwQuery dwQuery) {
        ComplexDwQueryEntity dwQueryEntity = new ComplexDwQueryEntity();
        dwQueryEntity.setDimension(dwQuery.getDimension().getName());
        dwQueryEntity.setFacts(prepareFacts(dwQuery.getFacts()));
        if (dwQuery.getWhereGroup() != null) {
            dwQueryEntity.setWhereGroup(prepareWhereGroup(dwQuery.getWhereGroup()));
        }
        return dwQueryEntity;
    }

    private Set<FactEntity> prepareFacts(List<Fact> facts) {
        Set<FactEntity> factEntities = new HashSet<>();
        for (Fact fact: facts) {
            factEntities.add(prepareFact(fact));
        }
        return factEntities;
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
            simpleDwQueryEntity.setWhereGroup(prepareWhereGroup(fact.getWhereGroup()));
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
        groupedByEntity.setFieldName(null); //what field name? fact's one?
        groupedByEntity.setHaving(prepareHaving(fact));
        groupedByEntity.setTableName(fact.getName());
        return groupedByEntity;
    }

    private HavingEntity prepareHaving(Fact fact) {
        HavingEntity having = new HavingEntity();
        having.setOperator(fact.getGroupedBy().getComparisonSymbol());
        having.setValue(fact.getGroupedBy().getValue());
        return having;
    }

}
