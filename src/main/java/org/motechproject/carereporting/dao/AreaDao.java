package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AreaEntity;

import java.util.List;

public interface AreaDao extends GenericDao<AreaEntity> {

    List<AreaEntity> getAllChildAreasByParentAreaId(Integer parentAreaId);
    List<AreaEntity> getDirectChildAreas(Integer parentAreaId);
    List<AreaEntity> getAreasByLevelId(Integer levelId);

}
