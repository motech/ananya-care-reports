package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AreaServiceImpl extends AbstractService implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LevelDao levelDao;

    @Override
    @Transactional
    public Set<AreaEntity> findAllAreas() {
        return areaDao.findAll();
    }

    @Override
    @Transactional
    public AreaEntity findAreaById(Integer areaId) {
        AreaEntity areaEntity = areaDao.findById(areaId);
        validateEntity(areaEntity);

        return areaEntity;
    }

    @Override
    @Transactional
    public Set<LevelEntity> findAllLevels() {
        return levelDao.findAll();
    }

    @Override
    @Transactional
    public LevelEntity findLevelById(Integer levelId) {
        LevelEntity levelEntity = levelDao.findById(levelId);
        validateEntity(levelEntity);

        return levelEntity;
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
