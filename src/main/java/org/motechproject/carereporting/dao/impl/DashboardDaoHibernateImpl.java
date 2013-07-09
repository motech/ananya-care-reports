package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.DashboardDao;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DashboardDaoHibernateImpl extends GenericDaoHibernateImpl<DashboardEntity> implements DashboardDao {

    @Override
    public Short getTabPositionForNewDashboard() {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        String tabPositionForNewDashboard =
                executeNamedQueryWithUniqueResult("dashboardEntity.getTabPositionForNewDashboard", queryParams).toString();
        return Short.valueOf(tabPositionForNewDashboard);
    }

    @Override
    public DashboardEntity findDashboardByName(String name) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("name", name);
        return (DashboardEntity) executeNamedQueryWithUniqueResult("dashboardEntity.findDashboardByName", queryParams);
    }

}
