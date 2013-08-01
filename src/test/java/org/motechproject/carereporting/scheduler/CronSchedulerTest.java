package org.motechproject.carereporting.scheduler;

import org.junit.Test;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CronSchedulerTest {

    private static final String EXPRESSION = "* * * * * ?";
    private static final String GROUP_NAME = "indicators";

    private final Scheduler scheduler;

    public CronSchedulerTest() throws SchedulerException {
        scheduler = new StdSchedulerFactory().getScheduler();
    }

    @Test
    public void testAddJob() throws SchedulerException {
        Integer id = 11;
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        CronTaskEntity cronTaskEntity = new CronTaskEntity(EXPRESSION);
        cronTaskEntity.setId(id);
        cronTaskEntity.setIndicator(indicatorEntity);
        CronScheduler cronScheduler = new CronScheduler();
        cronScheduler.setScheduler(scheduler);

        cronScheduler.addJob(cronTaskEntity);

        assertArrayEquals(new String[]{GROUP_NAME}, scheduler.getJobGroupNames());
        assertArrayEquals(new String[]{cronTaskEntity.getId().toString()}, scheduler.getJobNames(GROUP_NAME));
        assertNotNull(scheduler.getTrigger(cronTaskEntity.getId().toString(), GROUP_NAME));
    }

    @Test
    public void testUpdateJob() throws SchedulerException {
        Integer id = 11;
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        CronTaskEntity cronTaskEntity = new CronTaskEntity(EXPRESSION);
        cronTaskEntity.setId(id);
        cronTaskEntity.setIndicator(indicatorEntity);
        CronScheduler cronScheduler = new CronScheduler();
        Scheduler scheduler = mock(Scheduler.class);
        cronScheduler.setScheduler(scheduler);
        cronScheduler.addJob(cronTaskEntity);

        cronScheduler.updateJob(cronTaskEntity);

        verify(scheduler).rescheduleJob(eq(cronTaskEntity.getId().toString()), eq(GROUP_NAME), (Trigger) anyObject());
    }

    @Test
    public void testDeleteJob() throws SchedulerException {
        Integer id = 11;
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        CronTaskEntity cronTaskEntity = new CronTaskEntity(EXPRESSION);
        cronTaskEntity.setId(id);
        cronTaskEntity.setIndicator(indicatorEntity);
        CronScheduler cronScheduler = new CronScheduler();
        cronScheduler.setScheduler(scheduler);
        cronScheduler.addJob(cronTaskEntity);

        cronScheduler.deleteJob(cronTaskEntity);

        assertArrayEquals(new String[] {}, scheduler.getJobNames(GROUP_NAME));
    }

}
