package org.motechproject.carereporting.indicator;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.calculator.AbstractIndicatorValueCalculator;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Component
@Transactional
public class IndicatorValueCalculator {

    private static final Logger LOG = Logger.getLogger(IndicatorValueCalculator.class);

    //@Resource(name = "careDataSource")
    //private DataSource careDataSource;

    @Autowired
    private IndicatorService indicatorService;

    //@Autowired
    //private FormsService formsService;

    public void calculateIndicatorValues(FrequencyEntity frequencyEntity) {
        LOG.info("Running scheduled indicator values calculation.");
        int totalIndicatorValuesCalculated = calculateAllIndicatorValues(frequencyEntity);
        LOG.info("Scheduled indicator values calculation finished [total indicators values calculated = "
                + totalIndicatorValuesCalculated + "].");
    }

    @Transactional(readOnly = false)
    private int calculateAllIndicatorValues(FrequencyEntity frequencyEntity) {
        int calculatedIndicatorsCount = 0;
        Set<IndicatorEntity> indicators = indicatorService.getAllIndicators();
        for (IndicatorEntity indicator: indicators) {
            AbstractIndicatorValueCalculator calculator = getIndicatorValueCalculator(indicator);
            calculator.calculateAndPersistIndicatorValues(frequencyEntity);
            ++calculatedIndicatorsCount;
            LOG.info("Calculating values for indicator: " + indicator.getName() + " finished.");
        }
        return calculatedIndicatorsCount;
    }

    private AbstractIndicatorValueCalculator getIndicatorValueCalculator(IndicatorEntity indicator) {
        return new AbstractIndicatorValueCalculator(null, indicator, null, null) {
        };
    }

}
