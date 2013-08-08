package org.motechproject.carereporting.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ExportServiceIT {

    private static final byte[] BYTES = {34, 110, 97, 109, 101, 34, 9, 34, 110, 97, 109, 101, 34, 9, 34, 49, 48, 34, 10};

    @Autowired
    private ExportService exportService;

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
    }

    @Test
    public void testExportIndicatorValues() throws IOException {
        byte[] bytes = exportService.convertIndicatorValuesToBytes(indicatorValueEntityList);

        assertArrayEquals(BYTES, bytes);
    }
}
