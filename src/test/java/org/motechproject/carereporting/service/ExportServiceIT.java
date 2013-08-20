package org.motechproject.carereporting.service;

import org.apache.commons.lang.time.DateUtils;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ExportServiceIT {

    private static final String csvValue = "\"Area\",\"Frequency\",\"Date\",\"Value\"\n\"name\",\"name\",\"2013-08-20\",\"10\"\n";

    @Autowired
    private ExportService exportService;

    private List<IndicatorValueEntity> indicatorValueEntityList;

    @Before
    public void setup() throws IOException, ParseException {
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
        indicatorValueEntity.setDate(DateUtils.parseDate("20/08/2013", new String[] {"dd/MM/yyyy"}));
        indicatorValueEntityList = new ArrayList<>();
        indicatorValueEntityList.add(indicatorValueEntity);
    }

    @Test
    // test won't pass if delimiter in csv file will be different than ','
    public void testExportIndicatorValues() throws IOException, ParseException {
        byte[] bytes = exportService.convertIndicatorValuesToBytes(indicatorValueEntityList);

        assertArrayEquals(csvValue.getBytes(), bytes);
    }
}
