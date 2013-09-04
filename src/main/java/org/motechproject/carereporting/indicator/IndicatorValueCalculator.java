package org.motechproject.carereporting.indicator;

import org.apache.commons.lang.time.DateUtils;
import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class IndicatorValueCalculator {

    private static final int SCALE = 4;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private AreaDao areaDao;

    protected IndicatorService getIndicatorService() {
        return indicatorService;
    }

    public void calculateAndPersistIndicatorValue(IndicatorEntity indicator, FrequencyEntity frequency, Date from, Date to) {
        for (AreaEntity area : getIndicatorAreas(indicator)) {
            IndicatorValueEntity value = calculateIndicatorValueForArea(indicator, frequency, area, from, to);
            value.setArea(area);
            value.setDate(DateUtils.addSeconds(to, -1));
            value.setFrequency(frequency);
            value.setIndicator(indicator);

            if (isPercentageIndicator(indicator)) {
                value.setValue(BigDecimal.valueOf(100).multiply(value.getValue()));
            }

            persistIndicatorValue(value);
        }
    }

    private boolean isPercentageIndicator(IndicatorEntity indicator) {
        return indicator.getName().startsWith("%");
    }

    private Set<AreaEntity> getIndicatorAreas(IndicatorEntity indicator) {
        AreaEntity area = areaDao.getByIdWithFields(indicator.getArea().getId(), "childAreas");
        Set<AreaEntity> areas = new HashSet<>(area.getChildAreas());
        areas.add(area);
        return areas;
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
