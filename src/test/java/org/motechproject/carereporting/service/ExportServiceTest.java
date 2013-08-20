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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = "classpath:testContext.xml")
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class ExportServiceTest {

    private static final byte[] BYTES = {12, 13, 15, 100};

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
        indicatorValueEntity.setDate(new Date());
        indicatorValueEntityList = new ArrayList<IndicatorValueEntity>();
        indicatorValueEntityList.add(indicatorValueEntity);
        when(csvExportHelper.convertToCsvFile(anyList())).thenReturn(new ByteArrayInputStream(BYTES));
    }

    @Test
    public void testExportIndicatorValues() throws IOException, ParseException {
        byte [] returned = exportService.convertIndicatorValuesToBytes(indicatorValueEntityList);

        verify(csvExportHelper).convertToCsvFile(anyList());

        assertArrayEquals(BYTES, returned);
    }

    @Test(expected = CareNoValuesException.class)
    public void shouldThrowCareNoValuesExceptionWhenListOfIndicatorsValuesIsEmpty() throws IOException, ParseException {
        exportService.convertIndicatorValuesToBytes(new ArrayList<IndicatorValueEntity>());
    }
}
