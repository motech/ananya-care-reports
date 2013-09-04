package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class AreaDaoHibernateImpl extends GenericDaoHibernateImpl<AreaEntity> implements AreaDao {

    @Override
    public Set<AreaEntity> getAllChildAreasByParentAreaId(Integer parentAreaId) {
        Set<AreaEntity> areas = new LinkedHashSet<>();
        for (AreaEntity area: getDirectChildAreas(parentAreaId)) {
            areas.add(area);
            areas.addAll(getAllChildAreasByParentAreaId(area.getId()));
        }
        return areas;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<AreaEntity> getDirectChildAreas(Integer parentAreaId) {
        return new LinkedHashSet<AreaEntity>(getCurrentSession()
                .createCriteria(AreaEntity.class)
                .add(Restrictions.eq("parentArea.id", parentAreaId))
                .list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<AreaEntity> getAreasByLevelId(Integer levelId) {
        return new LinkedHashSet<AreaEntity>(getCurrentSession()
                .createQuery("from AreaEntity where level.id = :levelId")
                .setParameter("levelId", levelId)
                .list());
    }

}
