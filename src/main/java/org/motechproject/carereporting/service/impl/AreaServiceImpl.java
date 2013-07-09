package org.motechproject.carereporting.service.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class AreaServiceImpl extends AbstractService implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Set<AreaEntity> findAllAreas() {
        return areaDao.findAll();
    }

    @Override
    public Set<AreaEntity> findAreasByLevelId(Integer levelId) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from AreaEntity where level.id = :levelId");
        query.setParameter("levelId", levelId);

        return new LinkedHashSet<AreaEntity>(query.list());
    }

    @Override
    public Set<AreaEntity> findAreasByParentAreaId(Integer areaId) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from AreaEntity where parentArea.id = :areaId");
        query.setParameter("areaId", areaId);

        return new LinkedHashSet<AreaEntity>(query.list());
    }

    @Override
    @Transactional
    public AreaEntity findAreaById(Integer areaId) {
        return areaDao.findById(areaId);
    }

    @Override
    @Transactional
    public Set<LevelEntity> findAllLevels() {
        return levelDao.findAll();
    }

    @Override
    @Transactional
    public LevelEntity findLevelById(Integer levelId) {
        return levelDao.findById(levelId);
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
