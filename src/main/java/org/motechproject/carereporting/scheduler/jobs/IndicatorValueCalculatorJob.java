package org.motechproject.carereporting.scheduler.jobs;

import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.FrequencyEntity;
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

    public IndicatorValueCalculatorJob() {
        indicatorValueCalculator = applicationContext.getBean(IndicatorCalculator.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FrequencyEntity frequencyEntity = (FrequencyEntity) jobExecutionContext.getJobDetail().getJobDataMap().get(FREQUENCY);
        indicatorValueCalculator.calculateIndicatorValues(frequencyEntity, new Date());
    }
}
