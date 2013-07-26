package org.motechproject.carereporting.scheduler;

import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.scheduler.jobs.IndicatorValueCalculatorJob;
import org.motechproject.carereporting.service.CronService;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;

@Component
public class CronScheduler {

    private static final String TASK = "calculate_indicator_values";

    @Autowired
    private CronService cronService;

    @PostConstruct
    public void setupTrigger() throws SchedulerException, ParseException {
        JobDetail job = new JobDetail();
        job.setName("calculateIndicatorValues");
        job.setJobClass(IndicatorValueCalculatorJob.class);

        CronTaskEntity cronTaskEntity = cronService.getCronTaskByName(TASK);

        CronTrigger trigger = new CronTrigger();
        trigger.setName("careCronTrigger");
        trigger.setCronExpression(cronTaskEntity.toCronExpression());

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

}
