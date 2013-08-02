package org.motechproject.carereporting.scheduler.jobs;

import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.indicator.IndicatorValueCalculator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

public class IndicatorValueCalculatorJob implements Job {

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    private IndicatorValueCalculator indicatorValueCalculator;

    public IndicatorValueCalculatorJob() {
        indicatorValueCalculator = applicationContext.getBean(IndicatorValueCalculator.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        indicatorValueCalculator.calculateIndicatorValues();
    }
}
