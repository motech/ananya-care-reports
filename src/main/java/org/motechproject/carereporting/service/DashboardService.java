package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.forms.DashboardPosition;

import java.util.List;
import java.util.Set;

public interface DashboardService {

    Set<DashboardEntity> findAllDashboards();

    DashboardEntity findDashboardByName(String dashboardName);

    void createNewDashboard(DashboardEntity dashboardEntity);

    Short getTabPositionForNewDashboard();

    void saveDashboardsPositions(List<DashboardPosition> dashboardsPositions);
}
