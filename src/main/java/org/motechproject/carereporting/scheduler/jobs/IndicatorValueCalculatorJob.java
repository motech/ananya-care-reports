package org.motechproject.carereporting.scheduler.jobs;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.indicator.IndicatorCalculator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class IndicatorValueCalculatorJob implements Job {

    private static final String FREQUENCY = "frequency";

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    private IndicatorCalculator indicatorValueCalculator;

    private static final Logger LOGGER = Logger.getLogger(IndicatorValueCalculatorJob.class);

    public IndicatorValueCalculatorJob() {
        indicatorValueCalculator = applicationContext.getBean(IndicatorCalculator.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FrequencyEntity frequencyEntity = (FrequencyEntity) jobExecutionContext.getJobDetail().getJobDataMap().get(FREQUENCY);
        LOGGER.info("Cron job calculation started");
        try {
            indicatorValueCalculator.calculateIndicatorValues(frequencyEntity, new Date());
        } catch(RuntimeException e) {
            LOGGER.error("Cron job calculation failed");
            throw new CareRuntimeException(e);
        }
        LOGGER.info("Cron job calculation finished");
    }
}
