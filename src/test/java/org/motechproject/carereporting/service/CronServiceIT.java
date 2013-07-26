package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class CronServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private CronService cronService;

    @Test
    public void testGetCronTaskByName() {
        String name = "calculate_indicator_values";
        String cronExpression = "0 0 0 * * ?";
        CronTaskEntity cronTaskEntity = cronService.getCronTaskByName(name);

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getName());
        assertEquals(cronExpression, cronTaskEntity.toCronExpression());
    }

    @Test
    public void testUpdateCronTask() {
        String name = "calculate_indicator_values";
        String cronExpression = "0 0 3,4 * * 1-6";
        CronTaskEntity cronTaskEntity = cronService.getCronTaskByName(name);

        cronTaskEntity.setHour("3,4");
        cronTaskEntity.setWeekDay("1-6");

        cronService.updateCronTask(cronTaskEntity);

        cronTaskEntity = cronService.getCronTaskByName(name);

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getName());
        assertEquals(cronExpression, cronTaskEntity.toCronExpression());
    }

    @Test
    public void testGetDefaultCronTask() {
        String name = "calculate_indicator_values";

        CronTaskEntity cronTaskEntity = cronService.getDefaultCronTask();

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getName());
    }

}
