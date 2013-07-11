package org.motechproject.carereporting.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.impl.IndicatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorServiceTest {

    private final static String TEST_INDICATOR_1_UPDATED_NAME = "TEST_INDICATOR_1_UPDATED";

    @Mock
    private IndicatorDao indicatorDao;

    @InjectMocks
    private IndicatorService indicatorService = new IndicatorServiceImpl();

    private ArgumentCaptor<IndicatorEntity> indicatorEntityArgumentCaptor = ArgumentCaptor.forClass(IndicatorEntity.class);

    @Test
    public void testCreateNewIndicator() {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorService.createNewIndicator(indicatorEntity);

        verify(indicatorDao).save(indicatorEntityArgumentCaptor.capture());

        List<IndicatorEntity> indicatorEntities = indicatorEntityArgumentCaptor.getAllValues();
        assertEquals(1, indicatorEntities.size());
        assertEquals(indicatorEntity, indicatorEntities.get(0));
    }

    @Test
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
        when(indicatorDao.findAll()).thenReturn(indicatorEntities);
        Set<IndicatorEntity> indicatorList = indicatorService.findAllIndicators();

        verify(indicatorDao).findAll();

        assertEquals(1, indicatorList.size());
    }

    @Test
    public void testFindIndicatorById() {
        String name = "qwertyuiop";
        Integer id = 1;
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setId(id);
        indicatorEntity.setName(name);

        when(indicatorDao.findById(id)).thenReturn(indicatorEntity);
        IndicatorEntity returnedIndicator = indicatorService.findIndicatorById(id);

        verify(indicatorDao).findById(id);

        assertEquals(id, returnedIndicator.getId());
        assertEquals(name, returnedIndicator.getName());
    }

    @Test
    public void testDeleteIndicator() {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorService.createNewIndicator(indicatorEntity);
        indicatorService.deleteIndicator(indicatorEntity);

        verify(indicatorDao).remove(indicatorEntity);
    }

}
