package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Test
    public void testGetAllFrequencies() {
        String[] names = {"daily", "weekly", "monthly", "quarterly", "yearly"};
        FrequencyEntity[] frequencyEntities = cronService.getAllFrequencies().toArray(new FrequencyEntity[]{});

        assertNotNull(frequencyEntities);
        assertEquals(5, frequencyEntities.length);
        assertEquals(names[0], frequencyEntities[0].getFrequencyName());
        assertEquals(names[1], frequencyEntities[1].getFrequencyName());
        assertEquals(names[2], frequencyEntities[2].getFrequencyName());
        assertEquals(names[3], frequencyEntities[3].getFrequencyName());
        assertEquals(names[4], frequencyEntities[4].getFrequencyName());
    }

    @Test
    public void testGetFrequencyById() {
        Integer id = 1;
        String name = "yearly";
        FrequencyEntity frequencyEntity = cronService.getFrequencyById(id);

        assertEquals(name, frequencyEntity.getFrequencyName());
        assertEquals(id, frequencyEntity.getId());
    }

}
