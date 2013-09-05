package org.motechproject.carereporting.dao.impl;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.FormDao;
import org.motechproject.carereporting.domain.FormEntity;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@SuppressWarnings("unchecked")
public class FormDaoHibernateImpl extends GenericDaoHibernateImpl<FormEntity> implements FormDao {

    @Override
    public Set<FormEntity> getByTableName(String name) {
        return new LinkedHashSet<FormEntity>(getCurrentSession().createCriteria(FormEntity.class)
                .add(Restrictions.like("tableName", name, MatchMode.ANYWHERE))
                .list());
    }

    @Override
    public Set<FormEntity> getOtherTables() {
        return new LinkedHashSet<FormEntity>(getCurrentSession().createCriteria(FormEntity.class)
                .add(Restrictions.not(Restrictions.like("tableName", "mother", MatchMode.ANYWHERE)))
                .add(Restrictions.not(Restrictions.like("tableName", "child", MatchMode.ANYWHERE)))
                .list());
    }

}
