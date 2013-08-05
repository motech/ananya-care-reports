package org.motechproject.carereporting.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.LanguageDao;
import org.motechproject.carereporting.domain.LanguageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class LanguageDaoHibernateImpl extends GenericDaoHibernateImpl<LanguageEntity> implements LanguageDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public Set<LanguageEntity> getAllWithDefined(Boolean isDefined) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from LanguageEntity where defined = :isDefined");
        query.setParameter("isDefined", isDefined);

        return new LinkedHashSet<LanguageEntity>(query.list());
    }

    @Override
    public LanguageEntity getLanguageByCode(String languageCode) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from LanguageEntity where code = :languageCode");
        query.setParameter("languageCode", languageCode);

        return (LanguageEntity) query.list().get(0);
    }
}
