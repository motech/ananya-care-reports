package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.dto.DashboardPositionDto;

import java.util.List;
import java.util.Set;

public interface DashboardService {

    Set<DashboardEntity> getAllDashboards();

    DashboardEntity getDashboardByName(String dashboardName);

    void createNewDashboard(DashboardEntity dashboardEntity);

    Short getTabPositionForNewDashboard();

    void saveDashboardsPositions(List<DashboardPositionDto> dashboardsPositions);
}
