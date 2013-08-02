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

    private static final String TASK_NAME = "daily";

    @Test
    public void testGetByName() {
        String cronExpression = "0 00 00 * * ?";
        CronTaskEntity cronTaskEntity = cronTaskDao.getByName(TASK_NAME);

        assertNotNull(cronTaskEntity);
        assertEquals(TASK_NAME, cronTaskEntity.getName());
        assertEquals(cronExpression, cronTaskEntity.toString());
    }

}
