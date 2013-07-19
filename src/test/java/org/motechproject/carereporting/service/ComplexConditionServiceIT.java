package org.motechproject.carereporting.service;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.ComparisonSymbolEntity;
import org.motechproject.carereporting.domain.ComplexConditionEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.dto.ComplexConditionDto;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ComplexConditionServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ComplexConditionService complexConditionService;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private SessionFactory sessionFactory;

    private final static String CLEANUP_DELETE_COMPLEX_CONDITION_QUERY = "delete ComplexConditionEntity"
            + " where id = :complexConditionId";

    private final static int EXPECTED_COMPLEX_CONDITIONS_ALL = 1;
    private final static Integer COMPLEX_CONDITION_ID = 1;
    private final static Integer INDICATOR_ID = 1;
    private final static Integer CONDITION_ID = 1;
    private final static String COMPLEX_CONDITION_NAME = "TEST_COMPLEX_CONDITION_1";
    private final static String COMPLEX_CONDITION_UPDATED_NAME = "TEST_COMPLEX_CONDITION_UPDATED_1";
    private final static Integer OPERATOR_TYPE_ID = 1;
    private final static int EXPECTED_OPERATOR_TYPES_ALL = 4;
    private final static Integer COMPARISON_SYMBOL_ID = 1;
    private final static int EXPECTED_COMPARISON_SYMBOLS_ALL = 5;

    private static Integer newComplexConditionId;

    @Before
    public void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CAN_CREATE_COMPLEX_CONDITIONS"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials", authorities));
    }

    @After
    public void cleanup() {
        deleteCreatedComplexCondition();
    }

    private void deleteCreatedComplexCondition() {
        if (newComplexConditionId == null) {
            return;
        }

        Query query = sessionFactory.getCurrentSession()
                .createQuery(CLEANUP_DELETE_COMPLEX_CONDITION_QUERY);
        query.setParameter("complexConditionId", newComplexConditionId);
        query.executeUpdate();

        newComplexConditionId = null;
    }

    @Test
    public void testFindAllComplexConditions() {
        Set<ComplexConditionEntity> complexConditionEntities = complexConditionService.getAllComplexConditions();

        assertNotNull(complexConditionEntities);
        assertEquals(EXPECTED_COMPLEX_CONDITIONS_ALL, complexConditionEntities.size());
    }

    @Test
    public void testFindComplexConditionById() {
        ComplexConditionEntity complexConditionEntity =
                complexConditionService.getComplexConditionById(COMPLEX_CONDITION_ID);

        assertNotNull(complexConditionEntity);
        assertEquals(COMPLEX_CONDITION_ID, complexConditionEntity.getId());
    }

    @Test
    public void testCreateNewComplexCondition() {
        createComplexCondition();

        ComplexConditionEntity complexConditionEntity = complexConditionService
                .getComplexConditionById(newComplexConditionId);

        assertNotNull(complexConditionEntity);
        assertEquals(newComplexConditionId, complexConditionEntity.getId());
    }

    @Test
    public void testCreateNewComplexConditionFromDto() {
        ComplexConditionDto complexConditionDto = getNewComplexConditionDto();

        complexConditionService.createNewComplexCondition(complexConditionDto);

        ComplexConditionEntity complexConditionEntity = null;
        Set<ComplexConditionEntity> complexConditionEntities = complexConditionService.getAllComplexConditions();
        for (ComplexConditionEntity complexCondition : complexConditionEntities) {
            if (COMPLEX_CONDITION_NAME == complexCondition.getName()) {
                complexConditionEntity = complexCondition;
                break;
            }
        }

        assertNotNull(complexConditionEntity);
        assertEquals(COMPLEX_CONDITION_NAME, complexConditionEntity.getName());
    }

    @Test
    public void testUpdateComplexCondition() {
        createComplexCondition();

        ComplexConditionEntity complexConditionEntity = complexConditionService
                .getComplexConditionById(newComplexConditionId);

        assertNotNull(complexConditionEntity);
        assertEquals(newComplexConditionId, complexConditionEntity.getId());
        assertEquals(COMPLEX_CONDITION_NAME, complexConditionEntity.getName());

        complexConditionEntity.setName(COMPLEX_CONDITION_UPDATED_NAME);
        complexConditionService.updateComplexCondition(complexConditionEntity);

        complexConditionEntity = complexConditionService.getComplexConditionById(newComplexConditionId);

        assertNotNull(complexConditionEntity);
        assertEquals(newComplexConditionId, complexConditionEntity.getId());
        assertEquals(COMPLEX_CONDITION_UPDATED_NAME, complexConditionEntity.getName());
    }

    @Test
    public void testUpdateComplexConditionFromDto() {
        createComplexCondition();
        ComplexConditionDto complexConditionDto = getNewComplexConditionDto();

        ComplexConditionEntity complexConditionEntity = complexConditionService
                .getComplexConditionById(newComplexConditionId);

        assertNotNull(complexConditionEntity);
        assertEquals(newComplexConditionId, complexConditionEntity.getId());
        assertEquals(COMPLEX_CONDITION_NAME, complexConditionEntity.getName());

        complexConditionDto.setId(newComplexConditionId);
        complexConditionDto.setName(COMPLEX_CONDITION_UPDATED_NAME);
        complexConditionService.updateComplexCondition(complexConditionDto);

        complexConditionEntity = complexConditionService.getComplexConditionById(newComplexConditionId);

        assertNotNull(complexConditionEntity);
        assertEquals(newComplexConditionId, complexConditionEntity.getId());
        assertEquals(COMPLEX_CONDITION_UPDATED_NAME, complexConditionEntity.getName());
    }

    @Test(expected = CareResourceNotFoundRuntimeException.class)
    public void testDeleteComplexCondition() {
        ComplexConditionEntity complexConditionEntity = createComplexCondition();

        assertNotNull(complexConditionEntity);
        assertEquals(newComplexConditionId, complexConditionEntity.getId());

        complexConditionService.deleteComplexCondition(complexConditionEntity);

        complexConditionEntity = complexConditionService.getComplexConditionById(newComplexConditionId);

        assertNull(complexConditionEntity);
    }

    private ComplexConditionEntity createComplexCondition() {
        IndicatorEntity indicatorEntity = indicatorService.getIndicatorById(INDICATOR_ID);
        assertNotNull(indicatorEntity);
        assertEquals(INDICATOR_ID, indicatorEntity.getId());

        Set<IndicatorEntity> indicatorEntities = new LinkedHashSet<>();
        indicatorEntities.add(indicatorEntity);

        ConditionEntity conditionEntity = conditionService.getConditionEntityById(CONDITION_ID);
        assertNotNull(conditionEntity);
        assertEquals(CONDITION_ID, conditionEntity.getId());

        Set<ConditionEntity> conditionEntities = new LinkedHashSet<>();
        conditionEntities.add(conditionEntity);

        ComplexConditionEntity complexConditionEntity = new ComplexConditionEntity();
        complexConditionEntity.setName(COMPLEX_CONDITION_NAME);
        complexConditionEntity.setConditions(conditionEntities);
        complexConditionEntity.setIndicators(indicatorEntities);

        complexConditionService.createNewComplexCondition(complexConditionEntity);
        newComplexConditionId = complexConditionEntity.getId();

        return complexConditionEntity;
    }

    private ComplexConditionDto getNewComplexConditionDto() {
        ConditionEntity conditionEntity = conditionService.getConditionEntityById(CONDITION_ID);
        assertNotNull(conditionEntity);
        assertEquals(CONDITION_ID, conditionEntity.getId());

        Set<ConditionEntity> conditionEntities = new LinkedHashSet<>();
        conditionEntities.add(conditionEntity);

        ComplexConditionDto complexConditionDto = new ComplexConditionDto(
                COMPLEX_CONDITION_NAME, conditionEntities);

        assertNotNull(complexConditionDto);
        assertEquals(COMPLEX_CONDITION_NAME, complexConditionDto.getName());

        return complexConditionDto;
    }

    @Test
    public void testFindAllOperatorTypes() {
        Set<OperatorTypeEntity> operatorTypeEntities = complexConditionService.getAllOperatorTypes();
        
        assertNotNull(operatorTypeEntities);
        assertEquals(EXPECTED_OPERATOR_TYPES_ALL, operatorTypeEntities.size());
    }

    @Test
    public void testFindOperatorTypeById() {
        OperatorTypeEntity operatorTypeEntity = complexConditionService.getOperatorTypeById(OPERATOR_TYPE_ID);

        assertNotNull(operatorTypeEntity);
        assertEquals(OPERATOR_TYPE_ID, operatorTypeEntity.getId());
    }

    @Test
    public void testFindAllComparisonSymbols() {
        Set<ComparisonSymbolEntity> comparisonSymbolEntities = complexConditionService.getAllComparisonSymbols();

        assertNotNull(comparisonSymbolEntities);
        assertEquals(EXPECTED_COMPARISON_SYMBOLS_ALL, comparisonSymbolEntities.size());
    }

    @Test
    public void testFindComparisonSymbolById() {
        ComparisonSymbolEntity ComparisonSymbolEntity = complexConditionService.getComparisonSymbolById(
                COMPARISON_SYMBOL_ID);

        assertNotNull(ComparisonSymbolEntity);
        assertEquals(COMPARISON_SYMBOL_ID, ComparisonSymbolEntity.getId());
    }
}
