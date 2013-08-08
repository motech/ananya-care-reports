package org.motechproject.carereporting.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.exception.CareNoValuesException;
import org.motechproject.carereporting.export.csv.CsvExportHelper;
import org.motechproject.carereporting.service.impl.CsvExportServiceImpl;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ContextConfiguration(locations = "classpath:testContext.xml")
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class ExportServiceTest {

    @Mock
    private CsvExportHelper csvExportHelper;

    @InjectMocks
    private ExportService exportService = new CsvExportServiceImpl();

    private List<IndicatorValueEntity> indicatorValueEntityList;

    @Before
    public void setup() throws IOException {
        String name = "name";
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setName(name);
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setName(name);
        FrequencyEntity frequencyEntity = new FrequencyEntity();
        frequencyEntity.setFrequencyName(name);
        IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity();
        indicatorValueEntity.setIndicator(indicatorEntity);
        indicatorValueEntity.setFrequency(frequencyEntity);
        indicatorValueEntity.setArea(areaEntity);
        indicatorValueEntity.setValue(BigDecimal.TEN);
        indicatorValueEntityList = new ArrayList<IndicatorValueEntity>();
        indicatorValueEntityList.add(indicatorValueEntity);
        doNothing().when(csvExportHelper).saveCsvFile(anyList(), anyString());
    }

    @Test
    public void testExportIndicatorValuesWithDefaultPath() throws IOException {
        exportService.exportIndicatorValues(indicatorValueEntityList);

        verify(csvExportHelper).saveCsvFile(anyList(), anyString());
    }

    @Test
    public void testExportIndicatorValues() throws IOException {
        exportService.exportIndicatorValues(indicatorValueEntityList, "path");

        verify(csvExportHelper).saveCsvFile(anyList(), anyString());
    }

    @Test(expected = CareNoValuesException.class)
    public void shouldThrowCareNoValuesExceptionWhenListOfIndicatorsValuesIsEmpty() throws IOException {
        exportService.exportIndicatorValues(new ArrayList<IndicatorValueEntity>());
    }
}
