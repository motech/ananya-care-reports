package org.motechproject.carereporting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.IndicatorValueDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class IndicatorValueDaoHibernateImpl extends GenericDaoHibernateImpl<IndicatorValueEntity>
        implements IndicatorValueDao {

    @Autowired
    private AreaDao areaDao;

    @Override
    public List<IndicatorValueEntity> findIndicatorValuesForArea(Integer indicatorId, Integer areaId, Date earliestDate) {
        List<Integer> areasIds = new ArrayList<>();
        for (AreaEntity area: areaDao.findAllChildAreasByParentAreaId(areaId)) {
            areasIds.add(area.getId());
        }
        areasIds.add(areaId);
        Criteria criteria = getCurrentSession()
                .createCriteria(IndicatorValueEntity.class)
                .add(Restrictions.eq("indicator.id", indicatorId))
                .add(Restrictions.in("area.id", areasIds))
                .add(Restrictions.gt("date", earliestDate))
                .addOrder(Order.asc("date"));
        return new ArrayList<>(new LinkedHashSet<IndicatorValueEntity>(criteria.list()));
    }
}
