package org.motechproject.carereporting.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
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
    public void testGetByIndicatorId() {
        Integer id = 1;
        String name = "% of mothers who received at least 90 IFA tablets";
        String cronExpression = "0 0 0 * * ?";
        CronTaskEntity cronTaskEntity = cronTaskDao.getByIndicatorId(id);

        assertNotNull(cronTaskEntity);
        assertEquals(name, cronTaskEntity.getIndicator().getName());
        assertEquals(cronExpression, cronTaskEntity.toCronExpression());
    }

}
