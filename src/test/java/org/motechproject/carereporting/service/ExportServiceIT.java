package org.motechproject.carereporting.service;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class ExportServiceIT {

    @Autowired
    private ExportService exportService;

    @Value("${csv.export.path}")
    private String directory;

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
        File file = new File(directory);
        FileUtils.cleanDirectory(file);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy;HH:mm");
        String path = directory + "/" + indicatorValueEntityList.get(0).getIndicator().getName() + "." + simpleDateFormat.format(new Date()) + ".csv";

        exportService.exportIndicatorValues(indicatorValueEntityList);

        File created = new File(path);

        assertTrue(created.exists());
    }
}
