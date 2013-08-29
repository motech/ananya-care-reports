package org.motechproject.carereporting.performance.helpers;

import org.joda.time.DateTime;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.xml.XmlIndicatorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Transactional
public class PerformanceTestHelper {

    private static final Logger LOG = Logger.getLogger("PerformanceTestHelper");

    private static final String INDICATORS_DIRECTORY = "indicators/";
    private static final int CALCULATE_VALUES_FOR_PERIOD_DAYS = 300;
    private int indicatorsCount;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private XmlIndicatorParser xmlIndicatorParser;

    @Autowired
    private AreaService areaService;

    private Random random = new Random();

    public PerformanceTestHelper() {

    }

    public PerformanceTestHelper(int indicatorsCount) {
        this.indicatorsCount = indicatorsCount;
    }

    @Transactional(readOnly = false)
    public void populateDatabaseWithRandomIndicators() throws IOException, JAXBException {
        // TODO: Do it, if you can
//        File[] indicators = getAllIndicatorFiles();
//        while (--indicatorsCount >= 0) {
//            File indicator = indicators[random.nextInt(indicators.length)];
//            try {
//                createIndicator(indicator, "test-indicator-" + indicatorsCount);
//            } catch (CareRuntimeException e) {
//                LOG.warning("Cannot parse indicator: " + indicator.getPath());
//                LOG.info("Remained: " + indicatorsCount + " indicators");
//                indicatorsCount++;
//            }
//        }
    }

    private File[] getAllIndicatorFiles() {
        try {
            return new ClassPathResource(INDICATORS_DIRECTORY).getFile().listFiles();
        } catch (IOException e) {
            throw new RuntimeException("Cannot open indicators directory", e);
        }
    }

    private void createIndicator(File indicator, String name) throws IOException, JAXBException {
        IndicatorEntity indicatorEntity = xmlIndicatorParser.parse(new FileInputStream(indicator));
        indicatorEntity.setName(name);
        indicatorDao.save(indicatorEntity);
        for (AreaEntity area: areaService.getAllAreas()) {
            DateTime dateTime = new DateTime();
            for (int i=0; i<CALCULATE_VALUES_FOR_PERIOD_DAYS; i++) {
                addCalculatedValueForDate(indicatorEntity, area, dateTime.toDate());
                dateTime = dateTime.plusDays(1);
            }
        }
    }

    private void addCalculatedValueForDate(IndicatorEntity indicator, AreaEntity area, Date date) {
        IndicatorValueEntity value = new IndicatorValueEntity(indicator, area,
                BigDecimal.valueOf(random.nextDouble() * 1000),
                BigDecimal.valueOf(random.nextDouble() * 100),
                BigDecimal.valueOf(random.nextDouble() * 100),
                indicator.getDefaultFrequency(), date);

        indicatorService.createNewIndicatorValue(value);
    }

}
