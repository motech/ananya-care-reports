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
import java.util.Date;
import java.util.Set;

public class IndicatorValuesInitializer implements Runnable {

    private static final Logger LOG = Logger.getLogger(IndicatorValuesInitializer.class);

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    private IndicatorEntity indicatorEntity;

    private CronService cronService;

    private IndicatorValueCalculator indicatorValueCalculator;

    public IndicatorValuesInitializer(IndicatorEntity indicatorEntity) {
        this.indicatorEntity = indicatorEntity;
        cronService = applicationContext.getBean(CronService.class);
        indicatorValueCalculator = applicationContext.getBean(IndicatorValueCalculator.class);
    }

    // TODO: remove info
    @Override
    public void run() {
        LOG.info("Start calculation");
        Date startDate = null;
        try {
            // in database the earliest date (date_modified) is 02.01.1980, but from 01.12.2011 dates were inserted regularly
            startDate = DateUtils.parseDate("01/01/2012", new String[]{"dd/MM/yyyy"});
        } catch (ParseException e) {
            Logger.getLogger(IndicatorValuesInitializer.class).error(e);
        }
        Set<FrequencyEntity> frequencyEntities = cronService.getAllFrequencies();

        for (FrequencyEntity frequencyEntity: frequencyEntities) {
            Date endDate = DateResolver.resolveDates(frequencyEntity, new Date())[1];
            Date date = new Date(startDate.getTime());

            while (date.before(endDate)) {
                Date[] dates = DateResolver.resolveDates(frequencyEntity, date, date);
                indicatorValueCalculator.calculateAndPersistIndicatorValue(indicatorEntity, frequencyEntity, dates[0], dates[1]);
                date = dates[1];
            }
        }
        LOG.info("Calculation finished");
    }
}
