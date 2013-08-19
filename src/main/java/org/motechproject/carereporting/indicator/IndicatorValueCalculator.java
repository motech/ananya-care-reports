package org.motechproject.carereporting.indicator;

import org.apache.commons.lang.time.DateUtils;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public abstract class IndicatorValueCalculator {

    private static final int SCALE = 4;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private AreaService areaService;

    protected IndicatorService getIndicatorService() {
        return indicatorService;
    }

    public void calculateAndPersistIndicatorValue(IndicatorEntity indicator, FrequencyEntity frequency, Date from, Date to) {
        for (AreaEntity area : areaService.getAllAreas()) {
            IndicatorValueEntity value = calculateIndicatorValueForArea(indicator, frequency, area, from, to);
            value.setArea(area);
            value.setDate(DateUtils.addSeconds(to, -1));
            value.setFrequency(frequency);
            value.setIndicator(indicator);
            persistIndicatorValue(value);
        }
    }

    protected IndicatorValueEntity prepareIndicatorValueEntity(BigDecimal numeratorValue, BigDecimal denominatorValue) {
        IndicatorValueEntity value = new IndicatorValueEntity();
        value.setNumerator(numeratorValue);
        value.setDenominator(denominatorValue);

        BigDecimal indicatorValue = numeratorValue.divide(denominatorValue, SCALE, RoundingMode.HALF_UP);

        value.setValue(indicatorValue);
        return value;
    }

    protected void persistIndicatorValue(IndicatorValueEntity value) {
        indicatorService.createNewIndicatorValue(value);
    }

    protected abstract IndicatorValueEntity calculateIndicatorValueForArea(IndicatorEntity indicator, FrequencyEntity frequency, AreaEntity area, Date from, Date to);

}
