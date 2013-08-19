package org.motechproject.carereporting.initializers;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.indicator.IndicatorCalculator;
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

    private IndicatorCalculator indicatorCalculator;

    public IndicatorValuesInitializer(IndicatorEntity indicatorEntity) {
        this.indicatorEntity = indicatorEntity;
        cronService = applicationContext.getBean(CronService.class);
        indicatorCalculator = applicationContext.getBean(IndicatorCalculator.class);
    }

    @Override
    public void run() {
        LOG.info("Start calculation");

        Date startDate = null;
        try {
            startDate = DateUtils.parseDate(DateResolver.START_DATE, new String[]{"dd/MM/yyyy"});
        } catch (ParseException e) {
            Logger.getLogger(IndicatorValuesInitializer.class).error(e);
        }
        Set<FrequencyEntity> frequencyEntities = cronService.getAllFrequencies();

        for (FrequencyEntity frequencyEntity: frequencyEntities) {
            Date endDate = DateResolver.resolveDates(frequencyEntity, new Date())[1];
            Date date = new Date(startDate.getTime());

            while (date.before(endDate)) {
                Date[] dates = DateResolver.resolveDates(frequencyEntity, date, date);
                indicatorCalculator.calculateIndicatorValues(indicatorEntity, frequencyEntity, dates[0], dates[1]);
                date = dates[1];
            }
        }
        LOG.info("Calculation finished");
    }
}
