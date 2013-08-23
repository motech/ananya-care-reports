package org.motechproject.carereporting.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.ReportEntity;
import org.motechproject.carereporting.domain.dto.IndicatorDto;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore("Indicator structure has changed - these tests are outdated")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class IndicatorServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final int EXPECTED_INDICATORS_COUNT = 3;
    private static final int CATEGORY_ID = 2;
    private static final int EXPECTED_CATEGORY_INDICATORS_COUNT = 1;
    private static final int USER_ID = 1;
    private static final int INDICATOR_ID = 1;
    private static final int PARENT_AREA_ID = 1;
    private static final Date START_DATE = new Date();
    private static final Date END_DATE = new Date();
    private static final String NEW_INDICATOR_NAME = "new name";
    private static final Integer FREQUENCY_ID = 1;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private UserService userService;

    @Before
    public void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] permissions = {"CAN_CREATE_INDICATORS", "CAN_EDIT_INDICATORS", "CAN_REMOVE_INDICATORS",
            "CAN_CREATE_CATEGORIES", "CAN_EDIT_CATEGORIES", "CAN_REMOVE_CATEGORIES"};
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
    public void testGetIndicatorsByCategoryId() {
        assertEquals(EXPECTED_CATEGORY_INDICATORS_COUNT, indicatorService.getIndicatorsByCategoryId(CATEGORY_ID).size());
    }

    @Test
    public void testGetAllIndicatorsUnderUserArea() {
        assertNotNull(indicatorService.getAllIndicatorsUnderUserArea(USER_ID));
    }

    @Test
    public void testGetIndicatorById() {
        assertNotNull(indicatorService.getIndicatorById(INDICATOR_ID));
    }

    @Test
    public void testCreateNewIndicator() {
        int indicatorsCount = indicatorService.getAllIndicators().size();
        createIndicator();
        assertEquals(indicatorsCount + 1, indicatorService.getAllIndicators().size());
    }

    private void createIndicator() {
        IndicatorDto indicatorDto = new IndicatorDto(1, new HashSet<Integer>(), 1, new HashSet<Integer>(), 1, 1, new HashSet<Integer>(), new HashSet<ReportEntity>(), 30, "name", new BigDecimal(30));
        indicatorService.createNewIndicatorFromDto(indicatorDto);
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
    public void testGetAllIndicatorTypes() {
        assertNotNull(indicatorService.getAllIndicatorTypes());
    }

    @Test
    public void testGetAllIndicatorCategories() {
        assertNotNull(indicatorService.getAllIndicatorCategories());
    }

    @Test
    public void testCreateNewIndicatorCategory() {
        int indicatorCategoriesCount = indicatorService.getAllIndicatorCategories().size();
        createIndicatorCategory();
        assertEquals(indicatorCategoriesCount + 1, indicatorService.getAllIndicatorCategories().size());
    }

    private void createIndicatorCategory() {
        IndicatorCategoryEntity indicatorCategory = new IndicatorCategoryEntity("name");
        indicatorCategory.setShortCode("code");
        indicatorService.createNewIndicatorCategory(indicatorCategory);
    }

    @Test
    public void testUpdateIndicatorCategory() {
        String newName = "new name";
        IndicatorCategoryEntity indicatorCategory = indicatorService.getAllIndicatorCategories().iterator().next();
        indicatorCategory.setName(newName);
        indicatorService.updateIndicatorCategory(indicatorCategory);
        indicatorCategory = indicatorService.getIndicatorCategoryById(indicatorCategory.getId());
        assertEquals(newName, indicatorCategory.getName());
    }

    @Test
    public void testDeleteIndicatorCategory() {
        int indicatorCategoriesCount = indicatorService.getAllIndicatorCategories().size();
        createIndicatorCategory();
        assertEquals(indicatorCategoriesCount + 1, indicatorService.getAllIndicatorCategories().size());
        indicatorService.deleteIndicatorCategory(indicatorService.getAllIndicatorCategories().iterator().next());
        assertEquals(indicatorCategoriesCount, indicatorService.getAllIndicatorCategories().size());
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
                indicator.getArea(), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN, frequencyEntity, new Date());
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
                indicator.getArea(), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, frequencyEntity, new Date());
        indicatorService.createNewIndicatorValue(indicatorValueEntity);
        indicatorValueEntity.setValue(BigDecimal.TEN);
        indicatorService.updateIndicatorValue(indicatorValueEntity);
        indicatorValueEntity = indicatorService.getAllIndicatorValues().iterator().next();
        assertEquals(BigDecimal.TEN, indicatorValueEntity.getValue());
    }

    @Test
    public void testGetIndicatorsWithTrendsUnderUser() {
        assertNotNull(indicatorService.getIndicatorsWithTrendsUnderUser(userService.getCurrentlyLoggedUser(), new Date(), new Date(), null, null));
    }

    @Test
    public void testSetComputingForIndicator() {
        indicatorService.setComputingForIndicator(indicatorService.getIndicatorById(INDICATOR_ID), false);
        IndicatorEntity indicatorEntity = indicatorService.getIndicatorById(INDICATOR_ID);
        assertEquals(Boolean.TRUE, indicatorEntity.getComputed());
    }

}
