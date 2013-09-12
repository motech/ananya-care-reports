package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.dao.DwQueryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.IndicatorValueDao;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.impl.IndicatorServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorServiceTest {

    private static final String TEST_INDICATOR_NAME = "TEST_INDICATOR_1";
    private static final Integer TEST_INDICATOR_ID = 1;
    private static final String TEST_INDICATOR_1_UPDATED_NAME = "TEST_INDICATOR_1_UPDATED";
    private static final String WITH_FIELDS_REPORTS = "reports";

    @Mock
    private IndicatorDao indicatorDao;

    @Mock
    private DwQueryDao dwQueryDao;

    @Mock
    private IndicatorValueDao indicatorValueDao;

    @InjectMocks
    private IndicatorService indicatorService = Mockito.spy(new IndicatorServiceImpl());

    private ArgumentCaptor<DwQueryEntity> dwQueryEntityArgumentCaptor = ArgumentCaptor.forClass(DwQueryEntity.class);
    private ArgumentCaptor<IndicatorEntity> indicatorEntityArgumentCaptor = ArgumentCaptor.forClass(IndicatorEntity.class);

    @Test
    public void testCreateNewIndicator() throws Exception {
        doNothing().when(indicatorService).calculateIndicator((IndicatorEntity) anyObject());

        DwQueryEntity dwQueryEntity = new DwQueryEntity();
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setNumerator(dwQueryEntity);
        indicatorService.createNewIndicator(indicatorEntity);

        verify(indicatorDao).save(indicatorEntityArgumentCaptor.capture());
        verify(indicatorService).calculateIndicator((IndicatorEntity) anyObject());

        List<IndicatorEntity> indicatorEntities = indicatorEntityArgumentCaptor.getAllValues();
        assertEquals(1, indicatorEntities.size());
        assertEquals(indicatorEntity, indicatorEntities.get(0));
    }

    @Test
    public void testCalculateAllIndicators() {
        Set <IndicatorEntity> indicatorEntities = new HashSet<>();
        indicatorEntities.add(new IndicatorEntity());
        when(indicatorDao.getAll()).thenReturn(indicatorEntities);
        doNothing().when(indicatorDao).update((IndicatorEntity) anyObject());
        doNothing().when(indicatorService).calculateIndicator((IndicatorEntity) anyObject());
        doNothing().when(indicatorValueDao).removeByIndicator((IndicatorEntity) anyObject());

        indicatorService.calculateAllIndicators(0);

        verify(indicatorService).calculateIndicator((IndicatorEntity) anyObject());
        verify(indicatorDao).update((IndicatorEntity) anyObject());
        verify(indicatorDao).getAll();
        verify(indicatorValueDao).removeByIndicator((IndicatorEntity) anyObject());
    }

    @Test
    public void testUpdateIndicator() {
        doNothing().when(indicatorService).calculateIndicator((IndicatorEntity) anyObject());

        IndicatorEntity indicatorEntity = new IndicatorEntity();
        DwQueryEntity dwQueryEntity = new DwQueryEntity();
        indicatorEntity.setNumerator(dwQueryEntity);
        indicatorService.createNewIndicator(indicatorEntity);

        indicatorEntity.setName(TEST_INDICATOR_1_UPDATED_NAME);
        indicatorService.updateIndicator(indicatorEntity);

        verify(indicatorDao).update(indicatorEntityArgumentCaptor.capture());

        List<IndicatorEntity> indicatorEntities = indicatorEntityArgumentCaptor.getAllValues();
        assertEquals(1, indicatorEntities.size());
        assertEquals(TEST_INDICATOR_1_UPDATED_NAME, indicatorEntities.get(0).getName());
    }

    @Test
    public void testFindAllIndicators() {
        Set <IndicatorEntity> indicatorEntities = new HashSet<>();
        indicatorEntities.add(new IndicatorEntity());
        when(indicatorDao.getAllWithFields(WITH_FIELDS_REPORTS)).thenReturn(indicatorEntities);
        Set<IndicatorEntity> indicatorList = indicatorService.getAllIndicators();

        verify(indicatorDao).getAllWithFields(WITH_FIELDS_REPORTS);

        assertEquals(1, indicatorList.size());
    }

    @Test
    public void testFindIndicatorById() {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setId(TEST_INDICATOR_ID);
        indicatorEntity.setName(TEST_INDICATOR_NAME);

        when(indicatorDao.getById(TEST_INDICATOR_ID)).thenReturn(indicatorEntity);
        IndicatorEntity returnedIndicator = indicatorService.getIndicatorById(TEST_INDICATOR_ID);

        verify(indicatorDao).getById(TEST_INDICATOR_ID);

        assertEquals(TEST_INDICATOR_ID, returnedIndicator.getId());
        assertEquals(TEST_INDICATOR_NAME, returnedIndicator.getName());
    }

    @Test
    public void testDeleteIndicator() {
        doNothing().when(indicatorService).calculateIndicator((IndicatorEntity) anyObject());

        IndicatorEntity indicatorEntity = new IndicatorEntity();
        DwQueryEntity dwQueryEntity = new DwQueryEntity();
        indicatorEntity.setNumerator(dwQueryEntity);
        indicatorService.createNewIndicator(indicatorEntity);
        indicatorService.deleteIndicator(indicatorEntity);

        verify(indicatorDao).remove(indicatorEntity);
    }

}
