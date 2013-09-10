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

    private static final String TABLE_NAME = "tableName";

    @Override
    public Set<FormEntity> getByTableName(String name) {
        return new LinkedHashSet<FormEntity>(getCurrentSession().createCriteria(FormEntity.class)
                .add(Restrictions.like(TABLE_NAME, name, MatchMode.ANYWHERE))
                .add(Restrictions.or(
                        Restrictions.ilike(TABLE_NAME, "_form", MatchMode.END),
                        Restrictions.ilike(TABLE_NAME, "_case", MatchMode.END)))
                .list());
    }

    @Override
    public Set<FormEntity> getOtherTables() {
        return new LinkedHashSet<FormEntity>(getCurrentSession().createCriteria(FormEntity.class)
                .add(Restrictions.not(Restrictions.like(TABLE_NAME, "mother", MatchMode.ANYWHERE)))
                .add(Restrictions.not(Restrictions.like(TABLE_NAME, "child", MatchMode.ANYWHERE)))
                .add(Restrictions.or(
                        Restrictions.ilike(TABLE_NAME, "_form", MatchMode.END),
                        Restrictions.ilike(TABLE_NAME, "_case", MatchMode.END)))
                .list());
    }

}
