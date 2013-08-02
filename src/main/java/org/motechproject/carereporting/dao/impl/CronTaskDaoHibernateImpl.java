package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.CronTaskDao;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.springframework.stereotype.Component;

@Component
public class CronTaskDaoHibernateImpl extends GenericDaoHibernateImpl<CronTaskEntity> implements CronTaskDao {

    @Override
    public CronTaskEntity getByName(String name) {
        return (CronTaskEntity) getCurrentSession()
                .createCriteria(CronTaskEntity.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

}
