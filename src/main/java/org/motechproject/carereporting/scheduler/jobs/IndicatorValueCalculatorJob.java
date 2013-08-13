package org.motechproject.carereporting.scheduler.jobs;

import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.indicator.IndicatorValueCalculator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class IndicatorValueCalculatorJob implements Job {

    private static final String FREQUENCY = "frequency";

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    private IndicatorValueCalculator indicatorValueCalculator;

    public IndicatorValueCalculatorJob() {
        indicatorValueCalculator = applicationContext.getBean(IndicatorValueCalculator.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FrequencyEntity frequencyEntity = (FrequencyEntity) jobExecutionContext.getJobDetail().getJobDataMap().get(FREQUENCY);
        indicatorValueCalculator.calculateIndicatorValues(frequencyEntity);
    }
}
