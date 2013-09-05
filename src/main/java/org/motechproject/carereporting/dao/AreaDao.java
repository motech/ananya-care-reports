package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AreaEntity;

import java.util.Set;

public interface AreaDao extends GenericDao<AreaEntity> {

    Set<AreaEntity> getAllChildAreasByParentAreaId(Integer parentAreaId);
    Set<AreaEntity> getDirectChildAreas(Integer parentAreaId);
    Set<AreaEntity> getAreasByLevelId(Integer levelId);

    AreaEntity getAreaOnLevel(String areaName, String levelName);
}
