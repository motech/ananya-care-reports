package org.motechproject.carereporting.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class DashboardDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DashboardDao dashboardDao;

    @Test
    public void testGetTabPositionForNewDashboard() {
        Assert.assertEquals(Short.valueOf((short) 6), dashboardDao.getTabPositionForNewDashboard());
    }

    @Test
    public void testGetDashboardByName() {
        String name = "Performance summary";
        DashboardEntity dashboard = dashboardDao.getDashboardByName(name);
        Assert.assertEquals(name, dashboard.getName());
    }

    @Test
    public void testFailToGetDashboardByName() {
        String name = "Nonexistent dashboard";
        DashboardEntity dashboard = dashboardDao.getDashboardByName(name);
        Assert.assertNull(dashboard);
    }

}
