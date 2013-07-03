package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;

import java.util.Set;

public interface AreaService {

    Set<AreaEntity> getAllAreas();

    AreaEntity getAreaById(Integer areaId);

    Set<LevelEntity> getAllLevels();

    LevelEntity getLevelById(Integer levelId);

    void createNewArea(AreaEntity areaEntity);

    void updateArea(AreaEntity areaEntity);

    void deleteArea(AreaEntity areaEntity);

    void createNewLevel(LevelEntity level);
}
