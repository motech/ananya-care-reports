package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AreaEntity;

import java.util.List;

public interface AreaDao extends GenericDao<AreaEntity> {

    List<AreaEntity> findAllChildAreasByParentAreaId(Integer parentAreaId);
    List<AreaEntity> findDirectChildAreas(Integer parentAreaId);
    List<AreaEntity> findAreasByLevelId(Integer levelId);

}
