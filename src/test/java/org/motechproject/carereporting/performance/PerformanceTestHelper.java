package org.motechproject.carereporting.performance;

import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.xml.XmlIndicatorParser;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

public class PerformanceTestHelper {

    private static final Logger LOG = Logger.getLogger("PerformanceTestHelper");

    private String INDICATORS_DIRECTORY = "indicators/";

    private ApplicationContext applicationContext;

    private IndicatorService indicatorService;

    private XmlIndicatorParser xmlIndicatorParser;

    public PerformanceTestHelper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.indicatorService = applicationContext.getBean(IndicatorService.class);
        this.xmlIndicatorParser = applicationContext.getBean(XmlIndicatorParser.class);
    }

    public void populateDatabaseWithRandomIndicators(int indicatorsCount) {
        File[] indicators = getAllIndicatorFiles();
        Random random = new Random();
        while (--indicatorsCount >= 0) {
            File indicator = indicators[random.nextInt(indicators.length)];
            createIndicator(indicator, "test-indicator-" + indicatorsCount);
        }
    }

    private File[] getAllIndicatorFiles() {
        File indicatorsDir;
        try {
            indicatorsDir = new ClassPathResource(INDICATORS_DIRECTORY).getFile();
        } catch (IOException e) {
            throw new RuntimeException("Cannot open indicators directory", e);
        }
        return indicatorsDir.listFiles();
    }

    private void createIndicator(File indicator, String name) {
        try {
            IndicatorEntity indicatorEntity = xmlIndicatorParser.parse(new FileInputStream(indicator));
            indicatorEntity.setName(name);
            indicatorService.createNewIndicator(indicatorEntity);
        } catch (JAXBException | IOException e) {
            LOG.warning("Cannot parse indicator: " + indicator.getPath());
        }
    }

}
