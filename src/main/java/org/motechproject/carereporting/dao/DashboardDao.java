package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.DashboardEntity;

public interface DashboardDao extends GenericDao<DashboardEntity> {

    Short getTabPositionForNewDashboard();

    DashboardEntity findDashboardByName(String name);
}
