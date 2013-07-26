package org.motechproject.carereporting.dao;

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
public class CronTaskDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private CronTaskDao cronTaskDao;

    @Test
    public void testGetByName() {
        String name = "calculate_indicator_values";
        String cronExpression = "0 0 0 * * ?";
        CronTaskEntity cronTaskEntity = cronTaskDao.getByName(name);

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getName());
        assertEquals(cronExpression, cronTaskEntity.toCronExpression());
    }

}
