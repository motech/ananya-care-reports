package org.motechproject.carereporting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.IndicatorValueDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

@Component
public class IndicatorValueDaoHibernateImpl extends GenericDaoHibernateImpl<IndicatorValueEntity>
        implements IndicatorValueDao {
    
    private static final String DATE = "date";

    @Override
    @SuppressWarnings("unchecked")
    public List<IndicatorValueEntity> getIndicatorValuesForArea(Integer indicatorId, Integer areaId,
                                                                Date startDate, Date endDate) {
        Criteria criteria = getCurrentSession()
                .createCriteria(IndicatorValueEntity.class)
                .add(Restrictions.eq("indicator.id", indicatorId))
                .add(Restrictions.eq("area.id", areaId))
                .add(Restrictions.ge(DATE, startDate))
                .add(Restrictions.le(DATE, endDate))
                .addOrder(Order.asc(DATE));
        return new ArrayList<>(new LinkedHashSet<IndicatorValueEntity>(criteria.list()));
    }

    @SuppressWarnings("unchecked")
    public List<IndicatorValueEntity> findIndicatorValuesForArea(Integer indicatorId, Integer areaId) {
        Criteria criteria = getCurrentSession()
                .createCriteria(IndicatorValueEntity.class)
                .add(Restrictions.eq("indicator.id", indicatorId))
                .add(Restrictions.eq("area.id", areaId))
                .addOrder(Order.asc(DATE));
        return new ArrayList<>(new LinkedHashSet<IndicatorValueEntity>(criteria.list()));
    }

    @Override
    public IndicatorValueEntity getIndicatorValueClosestToDate(AreaEntity area, IndicatorEntity indicator, Date date) {
        List<IndicatorValueEntity> values = findIndicatorValuesForArea(indicator.getId(), area.getId());
        Long minDiff = null;
        IndicatorValueEntity value = null;
        for (IndicatorValueEntity loopValue: values) {
            long diff = Math.abs(date.getTime() - loopValue.getDate().getTime());
            if (minDiff == null || diff < minDiff) {
                minDiff = diff;
                value = loopValue;
            }
        }
        return value;
    }
}
