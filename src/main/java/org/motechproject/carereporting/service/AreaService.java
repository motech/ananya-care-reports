package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;

import java.util.Set;

public interface AreaService {

    Set<AreaEntity> getAllAreas();

    Set<AreaEntity> getAreasByLevelId(Integer levelId);

    Set<AreaEntity> getAreasByParentAreaId(Integer areaId);

    Set<AreaEntity> getAllAreasByParentAreaId(Integer areaId);

    Set<AreaEntity> getAllTopLevelAreas();

    AreaEntity getAreaById(Integer areaId);

    AreaEntity getAreaOnLevel(String areaName, String levelName);

    Set<LevelEntity> getAllLevels();

    LevelEntity getLevelById(Integer levelId);

    AreaEntity getByName(String name);

    void createNewArea(AreaEntity areaEntity);

    void updateArea(AreaEntity areaEntity);

    void deleteArea(AreaEntity areaEntity);

    void createNewLevel(LevelEntity level);

    AreaEntity prepareMockArea();
}
