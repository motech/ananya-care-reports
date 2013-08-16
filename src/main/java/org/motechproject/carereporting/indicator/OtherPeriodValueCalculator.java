package org.motechproject.carereporting.indicator;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class OtherPeriodValueCalculator extends IndicatorValueCalculator {

    @Override
    protected IndicatorValueEntity calculateIndicatorValueForArea(IndicatorEntity indicator, FrequencyEntity frequency, AreaEntity area, Date from, Date to) {
        FrequencyEntity frequencyEntity = getChildFrequency(frequency);
        List<IndicatorValueEntity> valueEntities = getIndicatorService().getIndicatorValuesForArea(indicator.getId(), area.getId(), frequencyEntity.getId(), from, to);

        BigDecimal numeratorValue = BigDecimal.ZERO;
        BigDecimal denominatorValue = BigDecimal.ZERO;

        for (IndicatorValueEntity indicatorValueEntity : valueEntities) {
            numeratorValue = numeratorValue.add(indicatorValueEntity.getNumerator());
            denominatorValue = denominatorValue.add(indicatorValueEntity.getDenominator());
        }

        denominatorValue = indicator.getDenominator() != null
                ? denominatorValue
                : BigDecimal.ONE;

        return prepareIndicatorValueEntity(numeratorValue, denominatorValue);
    }

    private FrequencyEntity getChildFrequency(FrequencyEntity frequency) {
        FrequencyEntity frequencyEntity = frequency;

        switch (frequency.getFrequencyName()) {
            case "monthly":
                frequencyEntity = frequencyEntity.getChildFrequency();
            default:
                frequencyEntity = frequencyEntity.getChildFrequency();
                break;
        }
        return frequencyEntity;
    }

}
