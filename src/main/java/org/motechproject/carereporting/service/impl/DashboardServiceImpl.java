package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.DashboardDao;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.dto.DashboardPositionDto;
import org.motechproject.carereporting.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dashboardDao;

    @Override
    public Set<DashboardEntity> getAllDashboards() {
        return dashboardDao.getAll();
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
    @Transactional(readOnly = false)
    public void saveDashboardsPositions(List<DashboardPositionDto> dashboardsPositions) {
        for (DashboardPositionDto dashboardPosition: dashboardsPositions) {
            DashboardEntity dashboard = getDashboardByName(dashboardPosition.getName());
            dashboard.setTabPosition(dashboardPosition.getPosition().shortValue());
            dashboardDao.save(dashboard);
        }
    }

    @Override
    public DashboardEntity getDashboardByName(String dashboardName) {
        return dashboardDao.getDashboardByName(dashboardName);
    }

}
