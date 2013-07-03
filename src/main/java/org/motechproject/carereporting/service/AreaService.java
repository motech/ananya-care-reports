package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;

import java.util.List;

public interface AreaService {

    List<AreaEntity> getAllAreas();

    AreaEntity getAreaById(Integer areaId);

    List<LevelEntity> getAllLevels();

    LevelEntity getLevelById(Integer levelId);

    void createNewArea(AreaEntity areaEntity);

    void updateArea(AreaEntity areaEntity);

    void deleteArea(AreaEntity areaEntity);

    void createNewLevel(LevelEntity level);
}
