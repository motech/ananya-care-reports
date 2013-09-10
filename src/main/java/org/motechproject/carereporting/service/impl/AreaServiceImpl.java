package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LevelDao levelDao;

    private static final Integer SUPER_USER_AREA_ID = 1;

    @Override
    @Transactional
    public Set<AreaEntity> getAllAreas() {
        return areaDao.getAll();
    }

    @Override
    public Set<AreaEntity> getAreasByLevelId(Integer levelId) {
        return new LinkedHashSet<AreaEntity>(areaDao.getAreasByLevelId(levelId));
    }

    @Override
    public Set<AreaEntity> getAreasByParentAreaId(Integer areaId) {
        return new LinkedHashSet<AreaEntity>(areaDao.getDirectChildAreas(areaId));
    }

    @Override
    @Transactional
    public Set<AreaEntity> getAllAreasByParentAreaId(Integer areaId) {
        HashSet<AreaEntity> areaEntities = new LinkedHashSet<>();
        if (!areaId.equals(SUPER_USER_AREA_ID)) {
            areaEntities.add(areaDao.getById(areaId));
        }
        areaEntities.addAll(areaDao.getAllChildAreasByParentAreaId(areaId));
        return areaEntities;
    }

    @Override
    public Set<AreaEntity> getAllTopLevelAreas() {
        return getAreasByLevelId(2);
    }

    @Override
    @Transactional
    public AreaEntity getAreaById(Integer areaId) {
        return areaDao.getById(areaId);
    }

    @Override
    public AreaEntity getAreaOnLevel(String areaName, String levelName) {
        return areaDao.getAreaOnLevel(areaName, levelName);
    }


    @Override
    @Transactional
    public Set<LevelEntity> getAllLevels() {
        return levelDao.getAll();
    }

    @Override
    @Transactional
    public LevelEntity getLevelById(Integer levelId) {
        return levelDao.getById(levelId);
    }

    @Override
    @Transactional
    public AreaEntity getByName(String name) {
        return areaDao.getByField("name", name);
    }

    @Override
    @Transactional(readOnly = false)

    public void createNewArea(AreaEntity areaEntity) {
        areaDao.save(areaEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateArea(AreaEntity areaEntity) {
        areaDao.update(areaEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteArea(AreaEntity areaEntity) {
        areaDao.remove(areaEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void createNewLevel(LevelEntity level) {
        levelDao.save(level);
    }
}
