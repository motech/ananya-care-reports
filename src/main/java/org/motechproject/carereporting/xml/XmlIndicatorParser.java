package org.motechproject.carereporting.xml;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.ComparisonSymbolDao;
import org.motechproject.carereporting.dao.ComputedFieldDao;
import org.motechproject.carereporting.dao.FormDao;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorTypeDao;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.DateDiffComparisonConditionEntity;
import org.motechproject.carereporting.domain.FieldComparisonConditionEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

@Component
@Transactional(readOnly = true)
public class XmlIndicatorParser {

    private static final String NAME_FIELD = "name";

    @Autowired
    private ComparisonSymbolDao comparisonSymbolDao;

    @Autowired
    private ComputedFieldDao computedFieldDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private IndicatorTypeDao indicatorTypeDao;

    @Autowired
    private FormDao formDao;

    @Autowired
    private IndicatorCategoryDao indicatorCategoryDao;

    @Transactional
    @SuppressWarnings("unchecked")
    public IndicatorEntity parse(InputStream is) throws DocumentException, SAXException, IOException {
        Element indicator = new SAXReader().read(is).getRootElement();
        String name = getElementValue(indicator, NAME_FIELD);
        String frequency = getElementValue(indicator, "frequency");
        String type = getElementValue(indicator, "type");
        String formName = getElementValue(indicator, "form");
        String areaName = getElementValue(indicator, "area");
        String computedFieldName = getElementValue(indicator, "computedField");

        Set<IndicatorCategoryEntity> categories = new HashSet<>();
        List<DefaultAttribute> categoryNames = indicator.selectNodes("categories/category/@name");
        for (DefaultAttribute attr: categoryNames) {
            categories.add(indicatorCategoryDao.getByField(NAME_FIELD, attr.getStringValue()));
        }

        List<Node> conditions = indicator.selectNodes("conditions/condition");
        ComplexConditionEntity complexConditionEntity = new ComplexConditionEntity();
        complexConditionEntity.setConditions(new HashSet<ConditionEntity>());
        complexConditionEntity.setName(name + "-complexCondition");
        for (Node condition: conditions) {
            ConditionEntity conditionEntity = createCondition(condition);
            conditionEntity.setComplexCondition(complexConditionEntity);
            complexConditionEntity.getConditions().add(conditionEntity);
        }

        return createIndicator(areaName, categories, complexConditionEntity,
                computedFieldName, formName, frequency, type, name);
    }

    private String getElementValue(Node node, String propertyName) {
         Node val = node.selectSingleNode(propertyName);
         return val != null ? val.getStringValue() : null;
    }

    private ConditionEntity createCondition(Node conditionNode) {
        String fieldName = getElementValue(conditionNode, "fieldName");
        String symbol = getElementValue(conditionNode, "symbol");
        String value = getElementValue(conditionNode, "value");
        String secondField = getElementValue(conditionNode, "secondField");
        String type = conditionNode.selectSingleNode("@type").getStringValue();

        ConditionEntity conditionEntity;

        switch (type) {
            case "fieldComparison":
                conditionEntity = createFieldComparisonCondition(secondField);
                break;
            case "dateDiff":
                conditionEntity = createDateDiffCondition(secondField, value);
                break;
            case "valueComparison":
            default:
                conditionEntity = createValueCondition(value);
        }

        conditionEntity.setComparisonSymbol(comparisonSymbolDao.getByField(NAME_FIELD, symbol));
        conditionEntity.setField1(findComputedFieldByName(fieldName));
        return conditionEntity;
    }

    private ConditionEntity createValueCondition(String value) {
        ValueComparisonConditionEntity valueCondition = new ValueComparisonConditionEntity();
        valueCondition.setValue(value);
        return valueCondition;
    }

    private ConditionEntity createDateDiffCondition(String secondField, String value) {
        DateDiffComparisonConditionEntity conditionEntity = new DateDiffComparisonConditionEntity();
        conditionEntity.setField2(findComputedFieldByName(secondField));
        conditionEntity.setValue(Integer.valueOf(value));
        return conditionEntity;
    }

    private ComputedFieldEntity findComputedFieldByName(String name) {
        return computedFieldDao.getByField(NAME_FIELD, name);
    }

    private ConditionEntity createFieldComparisonCondition(String secondField) {
        FieldComparisonConditionEntity conditionEntity = new FieldComparisonConditionEntity();
        conditionEntity.setField2(findComputedFieldByName(secondField));
        return conditionEntity;
    }

    private IndicatorEntity createIndicator(String areaName, Set<IndicatorCategoryEntity> categories,
                                            ComplexConditionEntity complexConditionEntity,
                                            String computedFieldName, String formName, String frequency,
                                            String type, String name) {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setArea(areaDao.getByField(NAME_FIELD, areaName));
        indicatorEntity.setCategories(categories);
        indicatorEntity.setComplexCondition(complexConditionEntity);

        Map<String, Object> fields = new HashMap<>();
        fields.put(NAME_FIELD, computedFieldName);
        FormEntity form = formDao.getByField("displayName", formName);
        fields.put("form.id", form.getId());
        indicatorEntity.setComputedField(computedFieldDao.getByFields(fields));

        indicatorEntity.setFrequency(getFrequencyFromString(frequency));
        indicatorEntity.setIndicatorType(indicatorTypeDao.getById(getIndicatorTypeIdFromName(type)));
        indicatorEntity.setName(name);
        indicatorEntity.setOwners(new HashSet<UserEntity>());
        indicatorEntity.setReports(new HashSet<ReportEntity>());
        indicatorEntity.setTrend(BigDecimal.ONE);
        indicatorEntity.setValues(new HashSet<IndicatorValueEntity>());

        return indicatorEntity;
    }

    private Integer getIndicatorTypeIdFromName(String typeName) {
        switch (typeName.toLowerCase()) {
            case "average":
                return 1;
            case "count":
                return 2;
            case "percentage":
                return 3;
            case "sum":
                return 4;
        }
        throw new IllegalArgumentException("Indicator type: " + typeName + " is invalid.");
    }

    private Integer getFrequencyFromString(String frequency) {
        switch (frequency) {
            case "monthly":
                return 30;
            case "weekly":
                return 7;
        }
        throw new IllegalArgumentException("Frequency " + frequency + " not supported.");
    }
}
