package org.motechproject.carereporting.dao.impl;

import org.hibernate.SessionFactory;
import org.motechproject.carereporting.dao.GenericDao;
import org.motechproject.carereporting.domain.AbstractEntity;
import org.motechproject.carereporting.exception.CareNullArgumentRuntimeException;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public abstract class GenericDaoHibernateImpl<T extends AbstractEntity> implements GenericDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> type;

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public GenericDaoHibernateImpl() {
        type = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<T> findAll() {
        return new HashSet<T>(sessionFactory.getCurrentSession()
                .createCriteria(type).list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(Integer id) {
        if (id == null) {
            throw new CareNullArgumentRuntimeException();
        }

        T entity = (T) sessionFactory.getCurrentSession().get(type, id);

        if (entity == null) {
            throw new CareResourceNotFoundRuntimeException(type, id);
        }

        return entity;
    }

    @Override
    public void save(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(T entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void remove(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public void removeAll() {
        sessionFactory.getCurrentSession()
                .createQuery("delete from " + type)
                .executeUpdate();
    }
}
