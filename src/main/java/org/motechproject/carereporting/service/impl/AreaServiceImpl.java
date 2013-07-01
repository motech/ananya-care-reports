package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LevelDao levelDao;

    @Override
    public List<AreaEntity> getAllAreas() {
        return areaDao.findAll();
    }

    @Override
    public List<LevelEntity> getAllLevels() {
        return levelDao.findAll();
    }

    @Override
    public void createNewArea(AreaEntity areaEntity) {
        areaDao.save(areaEntity);
    }

    @Override
    public void updateArea(AreaEntity areaEntity) {
        areaDao.update(areaEntity);
    }

    @Override
    public void deleteArea(AreaEntity areaEntity) {
        areaDao.remove(areaEntity);
    }

    @Override
    public void createNewLevel(LevelEntity level) {
        levelDao.save(level);
    }
}
