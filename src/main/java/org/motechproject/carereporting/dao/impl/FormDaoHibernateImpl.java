package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.FormDao;
import org.motechproject.carereporting.domain.FormEntity;
import org.springframework.stereotype.Component;

@Component
public class FormDaoHibernateImpl extends GenericDaoHibernateImpl<FormEntity> implements FormDao {

    @Override
    public FormEntity getByName(String name) {
        return (FormEntity) getCurrentSession().createCriteria(FormEntity.class)
                .add(Restrictions.eq("tableName", name))
                .uniqueResult();
    }
}
