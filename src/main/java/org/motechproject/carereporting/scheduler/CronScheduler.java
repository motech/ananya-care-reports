package org.motechproject.carereporting.scheduler;

import org.apache.log4j.Logger;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.scheduler.jobs.IndicatorValueCalculatorJob;
import org.motechproject.carereporting.service.CronService;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Set;

public class CronScheduler {

    @Autowired
    private CronService cronService;

    private Scheduler scheduler;

    private static final String GROUP_NAME = "indicators";

    private  static final Logger LOGGER = Logger.getLogger(CronScheduler.class);

    @PostConstruct
    public void setupTrigger() throws SchedulerException {
        scheduler = new StdSchedulerFactory().getScheduler();

        Set<CronTaskEntity> allCronTasks = cronService.getAllCronTasks();

        for (CronTaskEntity cronTaskEntity : allCronTasks) {
            addJob(cronTaskEntity);
        }

        scheduler.start();
    }

    public void addJob(CronTaskEntity cronTaskEntity) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("indicator", cronTaskEntity.getIndicator());

        JobDetail job = new JobDetail();
        job.setGroup(GROUP_NAME);
        job.setName(String.valueOf(cronTaskEntity.getId()));
        job.setJobClass(IndicatorValueCalculatorJob.class);
        job.setJobDataMap(jobDataMap);

        Trigger trigger = prepareTrigger(cronTaskEntity);

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOGGER.trace(e);
        }
    }

    public void updateJob(CronTaskEntity cronTaskEntity) {
        Trigger trigger = prepareTrigger(cronTaskEntity);
        try {
            scheduler.rescheduleJob(String.valueOf(cronTaskEntity.getId()), GROUP_NAME, trigger);
        } catch (SchedulerException e) {
            LOGGER.trace(e.getMessage());
        }
    }

    public void deleteJob(CronTaskEntity cronTaskEntity) {
        try {
            scheduler.deleteJob(String.valueOf(cronTaskEntity.getId()), GROUP_NAME);
        } catch (SchedulerException e) {
            LOGGER.trace(e.getMessage());
        }
    }

    private Trigger prepareTrigger(CronTaskEntity cronTaskEntity) {
        CronTrigger trigger = new CronTrigger();
        trigger.setJobGroup(GROUP_NAME);
        trigger.setJobName(String.valueOf(cronTaskEntity.getId()));
        trigger.setGroup(GROUP_NAME);
        trigger.setName(String.valueOf(cronTaskEntity.getId()));

        try {
            trigger.setCronExpression(cronTaskEntity.toCronExpression());
        } catch (ParseException e) {
            LOGGER.trace(e.getMessage());
        }

        return trigger;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
