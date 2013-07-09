package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.DashboardDao;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl extends AbstractService implements DashboardService {

    @Autowired
    private DashboardDao dashboardDao;

    @Override
    public Set<DashboardEntity> findAllDashboards() {
        return dashboardDao.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewDashboard(DashboardEntity dashboardEntity) {
        dashboardDao.save(dashboardEntity);
    }

    @Override
    public Short getTabPositionForNewDashboard() {
        return dashboardDao.getTabPositionForNewDashboard();
    }

    @Override
    public DashboardEntity findDashboardByName(String dashboardName) {
        return dashboardDao.findDashboardByName(dashboardName);
    }

}
