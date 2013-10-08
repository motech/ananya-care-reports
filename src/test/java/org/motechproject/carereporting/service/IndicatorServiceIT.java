package org.motechproject.carereporting.service;

import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorClassificationEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.SelectColumnEntity;
import org.motechproject.carereporting.domain.ValueComparisonConditionEntity;
import org.motechproject.carereporting.domain.WhereGroupEntity;
import org.motechproject.carereporting.domain.dto.DwQueryDto;
import org.motechproject.carereporting.domain.dto.GroupByDto;
import org.motechproject.carereporting.domain.dto.HavingDto;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
import org.motechproject.carereporting.domain.dto.SelectColumnDto;
import org.motechproject.carereporting.domain.dto.WhereConditionDto;
import org.motechproject.carereporting.domain.dto.WhereGroupDto;
import org.motechproject.carereporting.domain.types.ConditionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class IndicatorServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final int EXPECTED_INDICATORS_COUNT = 1;
    private static final int CLASSIFICATION_ID = 1;
    private static final int EXPECTED_CLASSIFICATION_INDICATORS_COUNT = 1;
    private static final int USER_ID = 1;
    private static final int INDICATOR_ID = 1;
    private static final int PARENT_AREA_ID = 1;
    private static final Date START_DATE = new Date();
    private static final Date END_DATE = new Date();
    private static final String NEW_INDICATOR_NAME = "new name";
    private static final Integer FREQUENCY_ID = 1;
    private static final String QUERY_NAME = "test_query";
    private static final String QUERY_DIMENSION = "mother_case";
    private static final String QUERY_DIMENSION_2 = "bp_form";
    private static final String QUERY_FIELD_NAME = "age";
    private static final String NEW_QUERY_NAME = "updated test query";
    private static final String FUNCTION_COUNT = "Count";
    private static final String QUERY_OPERATOR_GREATER_EQUAL = ">=";
    private static final String QUERY_OPERATOR_LESS_EQUAL = "<=";
    private static final String QUERY_COMPARISON_VALUE = "20";
    private static final String QUERY_COMPARISON_VALUE_2 = "25";
    private static final String QUERY_FIELD_NAME_2 = "ifa_tablets_issued";
    private static final String QUERY_JOIN_TYPE = "Join";
    private static final String QUERY_KEY_1 = "id";
    private static final String QUERY_KEY_2 = "case_id";
    private static final String OPERATOR_AND = "And";
    private static final String QUERY_FIELD_NAME_3 = "case_id";
    private static final int QUERY_UPDATED_GROUPS_COUNT = 1;
    private static final int QUERY_UPDATED_CONDITIONS_COUNT = 2;
    private static final int QUERY_UPDATED_COLUMNS_COUNT = 1;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private FormsService formsService;

    @Autowired
    private ComputedFieldService computedFieldService;

    @Before
    public void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] permissions = {"CAN_CREATE_INDICATORS", "CAN_EDIT_INDICATORS", "CAN_REMOVE_INDICATORS",
            "CAN_CREATE_CLASSIFICATIONS", "CAN_EDIT_CLASSIFICATIONS", "CAN_REMOVE_CLASSIFICATIONS"};
        for (String permission: permissions) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userService.getUserById(USER_ID), null, authorities));
    }

    @Test
    public void testGetAllIndicators() {
        assertEquals(EXPECTED_INDICATORS_COUNT, indicatorService.getAllIndicators().size());
    }

    @Test
    public void testGetIndicatorsByClassificationId() {
        assertEquals(EXPECTED_CLASSIFICATION_INDICATORS_COUNT, indicatorService.getIndicatorsByClassificationId(CLASSIFICATION_ID).size());
    }

    @Test
    public void testGetIndicatorById() {
        assertNotNull(indicatorService.getIndicatorById(INDICATOR_ID));
    }

    @Test
    @Ignore
    public void testCreateNewIndicator() {
        int indicatorsCount = indicatorService.getAllIndicators().size();
        createIndicator();
        assertEquals(indicatorsCount + 1, indicatorService.getAllIndicators().size());
    }

    private void createIndicator() {
        IndicatorDto indicatorDto = new IndicatorDto("name", new HashSet<Integer>(), 1, new HashSet<Integer>(), 1,
                new HashSet<ReportEntity>(), new BigDecimal(30), 1, null, false, false);
        indicatorService.createNewIndicator(indicatorService.createIndicatorEntityFromDto(indicatorDto));
    }

    @Test
    public void testGetIndicatorTrendForChildAreas() {
        assertNotNull(indicatorService.getIndicatorTrendForChildAreas(INDICATOR_ID, PARENT_AREA_ID, FREQUENCY_ID, START_DATE, END_DATE));
    }

    @Test
    public void testUpdateIndicator() {
        IndicatorEntity indicator = indicatorService.getIndicatorById(INDICATOR_ID);
        indicator.setName(NEW_INDICATOR_NAME);
        indicatorService.updateIndicator(indicator);
        indicator = indicatorService.getIndicatorById(INDICATOR_ID);
        assertEquals(NEW_INDICATOR_NAME, indicator.getName());
    }

    @Test
    public void testGetAllIndicatorClassifications() {
        assertNotNull(indicatorService.getAllIndicatorClassifications());
    }

    @Test
    public void testCreateNewIndicatorClassification() {
        int indicatorClassificationsCount = indicatorService.getAllIndicatorClassifications().size();
        createIndicatorClassification();
        assertEquals(indicatorClassificationsCount + 1, indicatorService.getAllIndicatorClassifications().size());
    }

    private void createIndicatorClassification() {
        IndicatorClassificationEntity indicatorClassification = new IndicatorClassificationEntity("name");
        indicatorService.createNewIndicatorClassification(indicatorClassification);
    }

    @Test
    public void testUpdateIndicatorClassification() {
        String newName = "new name";
        IndicatorClassificationEntity indicatorClassification = indicatorService.getAllIndicatorClassifications().iterator().next();
        indicatorClassification.setName(newName);
        indicatorService.updateIndicatorClassification(indicatorClassification);
        indicatorClassification = indicatorService.getIndicatorClassificationById(indicatorClassification.getId());
        assertEquals(newName, indicatorClassification.getName());
    }

    @Test
    public void testDeleteIndicatorClassification() {
        int indicatorClassificationsCount = indicatorService.getAllIndicatorClassifications().size();
        indicatorService.deleteIndicatorClassification(indicatorService.getIndicatorClassificationById(CLASSIFICATION_ID));
        assertEquals(indicatorClassificationsCount - 1, indicatorService.getAllIndicatorClassifications().size());
    }

    @Test
    public void testGetAllIndicatorValues() {
        assertNotNull(indicatorService.getAllIndicatorValues());
    }

    @Test
    public void testCreateNewIndicatorValue() {
        Integer id = 1;
        IndicatorEntity indicator = indicatorService.getIndicatorById(INDICATOR_ID);
        FrequencyEntity frequencyEntity = new FrequencyEntity();
        frequencyEntity.setId(id);
        IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity(indicator,
                areaService.getAreaById(1), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN, frequencyEntity, new Date());
        indicatorService.createNewIndicatorValue(indicatorValueEntity);
        assertNotNull(indicatorService.getAllIndicatorValues().iterator().next());
    }

    @Test
    public void testUpdateIndicatorValue() {
        Integer id = 1;
        IndicatorEntity indicator = indicatorService.getIndicatorById(INDICATOR_ID);
        FrequencyEntity frequencyEntity = new FrequencyEntity();
        frequencyEntity.setId(id);
        IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity(indicator,
                areaService.getAreaById(1), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, frequencyEntity, new Date());
        indicatorService.createNewIndicatorValue(indicatorValueEntity);
        indicatorValueEntity.setValue(BigDecimal.TEN);
        indicatorService.updateIndicatorValue(indicatorValueEntity);
        indicatorValueEntity = indicatorService.getAllIndicatorValues().iterator().next();
        assertEquals(BigDecimal.TEN, indicatorValueEntity.getValue());
    }

    @Test
    public void testGetIndicatorsWithTrendsUnderUser() {
        assertNotNull(indicatorService.getIndicatorsWithTrendsUnderUser(userService.getCurrentlyLoggedUser(), new Date(), new Date(), 1, 5));
    }

    @Test
    public void testSetComputingForIndicator() {
        indicatorService.setComputedForIndicator(indicatorService.getIndicatorById(INDICATOR_ID), true);
        IndicatorEntity indicatorEntity = indicatorService.getIndicatorById(INDICATOR_ID);
        assertEquals(Boolean.TRUE, indicatorEntity.getComputed());
    }

    @Test
    public void testUpdateDwQuery() {
        DwQueryEntity dwQueryEntity = createDwQueryEntity();

        ComputedFieldEntity field1 = computedFieldService
                .getComputedFieldByFormAndFieldName(QUERY_DIMENSION, QUERY_FIELD_NAME);
        ValueComparisonConditionEntity conditionEntity = new ValueComparisonConditionEntity();
        conditionEntity.setField1(field1);
        conditionEntity.setOperator(computedFieldService.getComparisonSymbolByName(QUERY_OPERATOR_LESS_EQUAL));
        conditionEntity.setValue(QUERY_COMPARISON_VALUE_2);

        WhereGroupEntity whereGroupEntity = new WhereGroupEntity();
        whereGroupEntity.getConditions().add(conditionEntity);

        SelectColumnEntity selectColumnEntity = new SelectColumnEntity();
        selectColumnEntity.setComputedField(field1);
        selectColumnEntity.setFunctionName(null);
        selectColumnEntity.setNullValue(null);

        Set<SelectColumnEntity> selectColumns = new LinkedHashSet<>();
        selectColumns.add(selectColumnEntity);

        dwQueryEntity.setName(NEW_QUERY_NAME);
        dwQueryEntity.getWhereGroup().getConditions().add(new ValueComparisonConditionEntity(conditionEntity));
        dwQueryEntity.getWhereGroup().getWhereGroups().add(whereGroupEntity);
        dwQueryEntity.getGroupedBy().setHaving(null);
        dwQueryEntity.setCombination(null);
        dwQueryEntity.setSelectColumns(selectColumns);

        indicatorService.updateDwQuery(dwQueryEntity);
        DwQueryEntity updatedDwQueryEntity = indicatorService.getDwQueryById(dwQueryEntity.getId());

        assertNotNull(updatedDwQueryEntity);
        assertEquals(NEW_QUERY_NAME, updatedDwQueryEntity.getName());
        assertEquals(updatedDwQueryEntity.getWhereGroup().getWhereGroups().size(), QUERY_UPDATED_GROUPS_COUNT);
        assertEquals(updatedDwQueryEntity.getWhereGroup().getConditions().size(), QUERY_UPDATED_CONDITIONS_COUNT);
        assertNull(updatedDwQueryEntity.getGroupedBy().getHaving());
        assertNull(updatedDwQueryEntity.getCombination());
        assertEquals(updatedDwQueryEntity.getSelectColumns().size(), QUERY_UPDATED_COLUMNS_COUNT);

        indicatorService.deleteDwQuery(updatedDwQueryEntity);
    }

    private DwQueryEntity createDwQueryEntity() {
        ComputedFieldEntity computedFieldEntity = computedFieldService
                .getComputedFieldByFormAndFieldName(QUERY_DIMENSION, QUERY_FIELD_NAME);
        ComputedFieldEntity computedFieldEntity2 = computedFieldService
                .getComputedFieldByFormAndFieldName(QUERY_DIMENSION_2, QUERY_FIELD_NAME_2);
        ComputedFieldEntity computedFieldEntity3 = computedFieldService
                .getComputedFieldByFormAndFieldName(QUERY_DIMENSION_2, QUERY_FIELD_NAME_3);

        List<SelectColumnDto> combinationSelectColumns = new LinkedList<>();
        combinationSelectColumns.add(new SelectColumnDto(computedFieldEntity2, null, null));
        combinationSelectColumns.add(new SelectColumnDto(computedFieldEntity3, null, null));

        DwQueryDto combineWith = new DwQueryDto(
                QUERY_DIMENSION_2,
                combinationSelectColumns,
                new WhereGroupDto(new LinkedList<WhereGroupDto>(), new LinkedList<WhereConditionDto>(), null),
                null,
                null,
                null,
                null,
                null,
                QUERY_NAME + "_1"
        );

        List<SelectColumnDto> selectColumns = new LinkedList<>();
        selectColumns.add(new SelectColumnDto(null, FUNCTION_COUNT, null));
        selectColumns.add(new SelectColumnDto(computedFieldEntity, null, null));
        selectColumns.add(new SelectColumnDto(computedFieldEntity2, FUNCTION_COUNT, null));

        List<WhereConditionDto> conditions = new LinkedList<>();
        conditions.add(new WhereConditionDto(
                computedFieldEntity2,
                null,
                0,
                null,
                QUERY_OPERATOR_GREATER_EQUAL,
                ConditionType.VALUE_COMPARISON,
                QUERY_COMPARISON_VALUE,
                null,
                null,
                null
        ));
        WhereGroupDto whereGroupDto = new WhereGroupDto(new LinkedList<WhereGroupDto>(),
                new LinkedList<WhereConditionDto>(), OPERATOR_AND);
        whereGroupDto.setConditions(conditions);

        HavingDto havingDto = new HavingDto(FUNCTION_COUNT, QUERY_OPERATOR_GREATER_EQUAL, QUERY_COMPARISON_VALUE);
        GroupByDto groupByDto = new GroupByDto(computedFieldEntity, havingDto);

        DwQueryDto dwQueryDto = new DwQueryDto(
                QUERY_DIMENSION,
                selectColumns,
                whereGroupDto,
                QUERY_JOIN_TYPE,
                QUERY_KEY_1,
                QUERY_KEY_2,
                combineWith,
                groupByDto,
                QUERY_NAME
        );

        indicatorService.createNewDwQuery(dwQueryDto);
        return indicatorService.getDwQueryByName(dwQueryDto.getName());
    }

}
