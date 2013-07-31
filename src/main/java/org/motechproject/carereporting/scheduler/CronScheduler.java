package org.motechproject.carereporting.scheduler;

import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.scheduler.jobs.IndicatorValueCalculatorJob;
import org.motechproject.carereporting.service.CronService;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Set;

public class CronScheduler {

    @Autowired
    private CronService cronService;

    @PostConstruct
    public void setupTrigger() throws SchedulerException, ParseException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        Set<CronTaskEntity> allCronTasks = cronService.getAllCronTasks();

        for (CronTaskEntity cronTaskEntity : allCronTasks) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("indicator", cronTaskEntity.getIndicator());

            JobDetail job = new JobDetail();
            job.setName(cronTaskEntity.getIndicator().getName());
            job.setJobClass(IndicatorValueCalculatorJob.class);
            job.setJobDataMap(jobDataMap);

            CronTrigger trigger = new CronTrigger();
            trigger.setName(cronTaskEntity.getIndicator().getName());
            trigger.setCronExpression(cronTaskEntity.toCronExpression());

            scheduler.scheduleJob(job, trigger);
        }
    }

}
