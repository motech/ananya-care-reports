package org.motechproject.carereporting.dao.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;


@Component
public class IndicatorDaoHibernateImpl extends GenericDaoHibernateImpl<IndicatorEntity> implements IndicatorDao {

    @Override
    public Set<IndicatorEntity> getIndicatorsByCategoryId(Integer categoryId) {
        Criteria criteria = getCurrentSession()
                .createCriteria(IndicatorEntity.class)
                .createAlias("categories", "category")
                .add(Restrictions.eq("category.id", categoryId));
        return new LinkedHashSet<IndicatorEntity>(criteria.list());
    }
}