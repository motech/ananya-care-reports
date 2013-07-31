package org.motechproject.carereporting.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class CronServiceIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private CronService cronService;

    @Autowired
    private IndicatorService indicatorService;

    @Test
    public void testGetCronTaskByIndicatorId() {
        Integer id = 1;
        String name = "% of mothers who received at least 90 IFA tablets";
        String cronExpression = "0 0 0 * * ?";
        CronTaskEntity cronTaskEntity = cronService.getCronTaskByIndicatorId(id);

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getIndicator().getName());
        assertEquals(cronExpression, cronTaskEntity.toCronExpression());
    }

    @Test
    public void testUpdateCronTask() {
        Integer id = 1;
        String name = "% of mothers who received at least 90 IFA tablets";
        String cronExpression = "0 0 3,4 * * 1-6";
        CronTaskEntity cronTaskEntity = cronService.getCronTaskByIndicatorId(id);

        cronTaskEntity.setHour("3,4");
        cronTaskEntity.setWeekDay("1-6");

        cronService.updateCronTask(cronTaskEntity);

        cronTaskEntity = cronService.getCronTaskByIndicatorId(id);

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getIndicator().getName());
        assertEquals(cronExpression, cronTaskEntity.toCronExpression());
    }

    @Test
    public void testGetAllCronTasks() {
        Set<CronTaskEntity> cronTaskEntitySet = cronService.getAllCronTasks();

        assertNotNull(cronTaskEntitySet);
        assertEquals(2, cronTaskEntitySet.size());
    }

    @Test
    public void testCreateCronTask() {
        String expr = "*/33 * * * * ?";
        Integer id = 3;
        CronTaskEntity cronTaskEntity = new CronTaskEntity(expr);
        IndicatorEntity indicatorEntity = indicatorService.getIndicatorById(id);
        cronTaskEntity.setIndicator(indicatorEntity);
        cronService.createCronTask(cronTaskEntity);

        cronTaskEntity = cronService.getCronTaskByIndicatorId(id);

        assertNotNull(cronTaskEntity);
        assertEquals(id, cronTaskEntity.getIndicator().getId());
        assertEquals(expr, cronTaskEntity.toCronExpression());
    }
}
