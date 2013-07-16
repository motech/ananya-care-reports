package org.motechproject.carereporting.indicator;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class IndicatorValueCalculatorScheduler {

    private static final Logger LOG = Logger.getLogger(IndicatorValueCalculatorScheduler.class);

    @Resource(name = "careDataSource")
    private DataSource careDataSource;

    @Autowired
    private IndicatorService indicatorService;

    @Scheduled(cron = "${indicator.calculation.cronExpr}")
    public void calculateIndicatorValues() {
        LOG.info("Running scheduled indicator values calculation.");
        int totalIndicatorValuesCalculated = calculateAllIndicatorValues();
        LOG.info("Scheduled indicator values calculation finished [total indicators values calculated = "
                + totalIndicatorValuesCalculated + "].");
    }

    @Transactional(readOnly = false)
    private int calculateAllIndicatorValues() {
        int calculatedIndicatorsCount = 0;
        Set<IndicatorEntity> indicators = indicatorService.findAllIndicators();
        for (IndicatorEntity indicator: indicators) {
            LOG.info("Calculating values for indicator: " + indicator.getName());
            calculateAndPersistIndicatorValues(indicator);
            ++calculatedIndicatorsCount;
            LOG.info("Calculating values for indicator: " + indicator.getName() + " finished.");
        }
        return calculatedIndicatorsCount;
    }

    private void calculateAndPersistIndicatorValues(IndicatorEntity indicator) {

        for (AreaEntity area: getAllAreasForIndicator(indicator)) {

            AbstractIndicatorValueCalculator calculator = getIndicatorValueCalculator(indicator);
            BigDecimal indicatorValue = calculator.calculateIndicatorValueForArea(area);

            IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity(new Date(),
                    indicator, area, indicatorValue);
            indicatorService.createNewIndicatorValue(indicatorValueEntity);
        }
    }

    private List<AreaEntity> getAllAreasForIndicator(IndicatorEntity indicator) {
        List<AreaEntity> areas = new ArrayList<>();
        areas.add(indicator.getArea());
        areas.addAll(getAllChildAreas(indicator.getArea()));
        return areas;
    }

    private List<AreaEntity> getAllChildAreas(AreaEntity area) {
        List<AreaEntity> childAreas = new ArrayList<>();
        for (AreaEntity child: area.getChildAreas()) {
            childAreas.add(child);
            childAreas.addAll(getAllChildAreas(child));
        }
        return childAreas;
    }

    private AbstractIndicatorValueCalculator getIndicatorValueCalculator(IndicatorEntity indicator) {
        switch (indicator.getIndicatorType().getName()) {
            case "Average":
                return new AverageIndicatorValueCalculator(careDataSource, indicator);
            case "Count":
                return new CountIndicatorValueCalculator(careDataSource, indicator);
            case "Percentage":
                return new PercentageIndicatorValueCalculator(careDataSource, indicator);
            case "Sum":
                return new SumIndicatorValueCalculator(careDataSource, indicator);
        }
        throw new IllegalArgumentException("Indicator type " +
                indicator.getIndicatorType().getName() + " not supported.");
    }

}
