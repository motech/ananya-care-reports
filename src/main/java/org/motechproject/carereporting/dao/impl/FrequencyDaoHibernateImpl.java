package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.FrequencyDao;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.springframework.stereotype.Component;

@Component
public class FrequencyDaoHibernateImpl extends GenericDaoHibernateImpl<FrequencyEntity> implements FrequencyDao {

    @Override
    public FrequencyEntity getByFrequencyName(String name) {
        return (FrequencyEntity) getCurrentSession()
                .createCriteria(FrequencyEntity.class)
                .add(Restrictions.eq("frequencyName", name))
                .uniqueResult();
    }
}
