package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.DashboardEntity;

import java.util.Set;

public interface DashboardService {

    Set<DashboardEntity> findAllDashboards();

    DashboardEntity findDashboardByName(String dashboardName);

    void createNewDashboard(DashboardEntity dashboardEntity);

    Short getTabPositionForNewDashboard();
}
