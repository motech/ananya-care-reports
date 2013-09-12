package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.DashboardDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.DashboardEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.dto.DashboardDto;
import org.motechproject.carereporting.domain.dto.DashboardPositionDto;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.CronService;
import org.motechproject.carereporting.service.DashboardService;
import org.motechproject.carereporting.service.UserService;
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

    @Autowired
    private CronService cronService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Override
    public Set<DashboardEntity> getAllDashboards() {
        return dashboardDao.getAllWithFields("reports");
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
    public DashboardEntity getDashboardById(Integer id) {
        return dashboardDao.getById(id);
    }

    @Override
    public DashboardDto getAllDashboardsFromDto() {
        Set<FrequencyEntity> frequencyEntities = cronService.getAllFrequencies();
        UserEntity userEntity = userService.getCurrentlyLoggedUser();
        Set<AreaEntity> areaEntities = areaService.getAllAreasByParentAreaId(userEntity.getArea().getId());
        Set<DashboardEntity> dashboardEntities = dashboardDao.getAll();
        DashboardEntity dashboardEntity = userEntity.getDefaultDashboard();

        return new DashboardDto(frequencyEntities, userEntity.getArea(), areaEntities, dashboardEntities, dashboardEntity);
    }

    @Override
    public DashboardEntity getDashboardByName(String dashboardName) {
        return dashboardDao.getDashboardByName(dashboardName);
    }

}
