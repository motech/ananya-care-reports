package org.motechproject.carereporting.indicator;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.utils.date.DateResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;

@Component
@Transactional
public class IndicatorCalculator {

    private static final Logger LOG = Logger.getLogger(IndicatorCalculator.class);
    private static final String DAILY = "daily";

    @Autowired
    private IndicatorService indicatorService;

    @Transactional(readOnly = false)
    public void calculateIndicatorValues(FrequencyEntity frequency, Date date) {
        Date[] dates = DateResolver.resolveDates(frequency, date);
        Date from = dates[0];
        Date to = dates[1];
        for (IndicatorEntity indicator: indicatorService.getAllIndicators()) {
            calculateIndicatorValues(indicator, frequency, from, to);
        }
    }

    public void calculateIndicatorValues(IndicatorEntity indicator, FrequencyEntity frequency, Date from, Date to) {
        IndicatorValueCalculator indicatorValueCalculator;
        Date start = from;

        if (DAILY.equals(frequency.getFrequencyName()) || indicator.getNumerator().getHasPeriodCondition() || !indicator.getAdditive() == Boolean.TRUE) {
            indicatorValueCalculator = ApplicationContextProvider.getApplicationContext().getBean(DailyValueCalculator.class);

            if (!indicator.getNumerator().getHasPeriodCondition() && !indicator.getAdditive()) {
                try {
                    start = DateUtils.parseDate(DateResolver.START_DATE, new String[]{"dd/MM/yyyy"});
                } catch (ParseException e) {
                    LOG.error("", e);
                }
            }
        } else {
            indicatorValueCalculator = ApplicationContextProvider.getApplicationContext().getBean(OtherPeriodValueCalculator.class);
        }

        indicatorValueCalculator.calculateAndPersistIndicatorValue(indicator, frequency, start, to);
    }
}
