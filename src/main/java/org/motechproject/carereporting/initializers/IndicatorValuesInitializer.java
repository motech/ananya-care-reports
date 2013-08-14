package org.motechproject.carereporting.initializers;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.IndicatorValueCalculator;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.utils.date.DateResolver;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class IndicatorValuesInitializer implements Runnable {

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    private IndicatorEntity indicatorEntity;

    private CronService cronService;

    private IndicatorValueCalculator indicatorValueCalculator;

    public IndicatorValuesInitializer(IndicatorEntity indicatorEntity) {
        this.indicatorEntity = indicatorEntity;
        cronService = applicationContext.getBean(CronService.class);
        indicatorValueCalculator = applicationContext.getBean(IndicatorValueCalculator.class);
    }

    @Override
    public void run() {
        Date startDate = null;
        try {
            // TODO: change date
            // the earliest date (date_modified) in database is 02.01.1980, but from 01.12.2011 dates were inserted regularly
            startDate = DateUtils.parseDate("01/08/2013/06", new String[]{"dd/MM/yyyy/HH"});
        } catch (ParseException e) {
            Logger.getLogger(IndicatorValuesInitializer.class).error(e);
        }
        Date endDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

        Set<FrequencyEntity> frequencyEntities = cronService.getAllFrequencies();

        for (FrequencyEntity frequencyEntity: frequencyEntities) {
            Date date = new Date(startDate.getTime());

            while (date.before(endDate)) {
                Date[] dates = DateResolver.resolveDates(frequencyEntity, date, date);
                indicatorValueCalculator.calculateAndPersistIndicatorValue(indicatorEntity, frequencyEntity, dates);
                date = DateUtils.addHours(dates[1], 6);
            }
        }
    }
}
