package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;

import java.util.Set;

public interface AreaService {

    Set<AreaEntity> findAllAreas();

    Set<AreaEntity> findAreasByLevelId(Integer levelId);

    Set<AreaEntity> findAreasByParentAreaId(Integer areaId);

    Set<AreaEntity> findAllChildAreasByParentAreaId(Integer areaId);

    AreaEntity findAreaById(Integer areaId);

    Set<LevelEntity> findAllLevels();

    LevelEntity findLevelById(Integer levelId);

    void createNewArea(AreaEntity areaEntity);

    void updateArea(AreaEntity areaEntity);

    void deleteArea(AreaEntity areaEntity);

    void createNewLevel(LevelEntity level);
}
