package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AreaDaoHibernateImpl extends GenericDaoHibernateImpl<AreaEntity> implements AreaDao {

    @Override
    public List<AreaEntity> findAllChildAreasByParentAreaId(Integer parentAreaId) {
        List<AreaEntity> areas = new ArrayList<>();
        for (AreaEntity area: findDirectChildAreas(parentAreaId)) {
            areas.add(area);
            areas.addAll(findAllChildAreasByParentAreaId(area.getId()));
        }
        return areas;
    }

    @Override
    public List<AreaEntity> findDirectChildAreas(Integer parentAreaId) {
        return getCurrentSession()
                .createCriteria(AreaEntity.class)
                .add(Restrictions.eq("parentArea.id", parentAreaId))
                .list();
    }

    @Override
    public List<AreaEntity> findAreasByLevelId(Integer levelId) {
        return getCurrentSession()
                .createQuery("from AreaEntity where level.id = :levelId")
                .setParameter("levelId", levelId)
                .list();
    }

}
