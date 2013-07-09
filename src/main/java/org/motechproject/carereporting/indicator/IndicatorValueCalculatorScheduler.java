package org.motechproject.carereporting.indicator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class IndicatorValueCalculatorScheduler {

    private static final Logger LOG = Logger.getLogger(IndicatorValueCalculatorScheduler.class);

    @Autowired
    private IndicatorValueCalculator indicatorValueCalculator;

    // @Scheduled(cron = "${indicator.calculation.cronExpr}")
    public void calculateIndicatorValues() {
        LOG.info("Running scheduled indicator values calculation.");

        int totalIndicatorValuesCalculated = indicatorValueCalculator.calculateAllIndicatorsValues();

        LOG.info("Scheduled indicator values calculation finished [total indicators values calculated = "
                + totalIndicatorValuesCalculated + "].");
    }
}
