package org.motechproject.carereporting.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.impl.IndicatorServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorServiceTest {

    private final static String TEST_INDICATOR_NAME = "TEST_INDICATOR_1";
    private final static Integer TEST_INDICATOR_ID = 1;
    private final static String TEST_INDICATOR_1_UPDATED_NAME = "TEST_INDICATOR_1_UPDATED";
    private final static String WITH_FIELDS_REPORTS = "reports";

    @Mock
    private IndicatorDao indicatorDao;

    @InjectMocks
    private IndicatorService indicatorService = new IndicatorServiceImpl();

    private ArgumentCaptor<IndicatorEntity> indicatorEntityArgumentCaptor = ArgumentCaptor.forClass(IndicatorEntity.class);

    // TODO: Mock thread creation if you can, I can't :(
    // Tip: use powermock
    @Test
    @Ignore
    public void testCreateNewIndicator() throws Exception {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorService.createNewIndicator(indicatorEntity);

        verify(indicatorDao).save(indicatorEntityArgumentCaptor.capture());

        List<IndicatorEntity> indicatorEntities = indicatorEntityArgumentCaptor.getAllValues();
        assertEquals(1, indicatorEntities.size());
        assertEquals(indicatorEntity, indicatorEntities.get(0));
    }

    // TODO: Mock thread creation if you can, I can't :(
    // Tip: use powermock
    @Test
    @Ignore
    public void testUpdateIndicator() {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
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

        when(indicatorDao.getByIdWithFields(TEST_INDICATOR_ID, WITH_FIELDS_REPORTS)).thenReturn(indicatorEntity);
        IndicatorEntity returnedIndicator = indicatorService.getIndicatorById(TEST_INDICATOR_ID);

        verify(indicatorDao).getByIdWithFields(TEST_INDICATOR_ID, WITH_FIELDS_REPORTS);

        assertEquals(TEST_INDICATOR_ID, returnedIndicator.getId());
        assertEquals(TEST_INDICATOR_NAME, returnedIndicator.getName());
    }

    // TODO: Mock thread creation if you can, I can't :(
    // Tip: use powermock
    @Test
    @Ignore
    public void testDeleteIndicator() {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorService.createNewIndicator(indicatorEntity);
        indicatorService.deleteIndicator(indicatorEntity);

        verify(indicatorDao).remove(indicatorEntity);
    }

}
