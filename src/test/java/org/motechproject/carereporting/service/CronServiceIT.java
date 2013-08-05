package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class CronServiceIT extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private CronService cronService;

    private static final String TASK_NAME = "weekly";

    @Test
    public void testGetCronTaskByFrequencyName() {
        String cronExpression = "0 0 0 ? * MON";
        CronTaskEntity cronTaskEntity = cronService.getCronTaskByFrequencyName(TASK_NAME);

        assertNotNull(cronTaskEntity);
        assertEquals(TASK_NAME, cronTaskEntity.getFrequency().getFrequencyName());
        assertEquals(cronExpression, cronTaskEntity.toString());
    }

    @Test
    public void testGetDailyCronTask() {
        String defaultName = "daily";
        String cronExpression = "0 00 00 * * ?";
        CronTaskEntity cronTaskEntity = cronService.getDailyCronTask();

        assertNotNull(cronTaskEntity);
        assertEquals(defaultName, cronTaskEntity.getFrequency().getFrequencyName());
        assertEquals(cronExpression, cronTaskEntity.toString());
    }

    @Test
    public void testUpdateCronTask() {
        String cronExpression = "0 0 3,4 ? * 1-6";
        CronTaskEntity cronTaskEntity = cronService.getCronTaskByFrequencyName(TASK_NAME);

        cronTaskEntity.setHour("3,4");
        cronTaskEntity.setWeekDay("1-6");

        cronService.updateCronTask(cronTaskEntity);

        cronTaskEntity = cronService.getCronTaskByFrequencyName(TASK_NAME);

        assertNotNull(cronTaskEntity);
        assertEquals(TASK_NAME, cronTaskEntity.getFrequency().getFrequencyName());
        assertEquals(cronExpression, cronTaskEntity.toString());
    }

    @Test
    public void testGetAllCronTasks() {
        Set<CronTaskEntity> cronTaskEntitySet = cronService.getAllCronTasks();

        assertNotNull(cronTaskEntitySet);
        assertEquals(5, cronTaskEntitySet.size());
    }

}
