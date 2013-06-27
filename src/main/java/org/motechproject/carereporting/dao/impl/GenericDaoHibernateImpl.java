package org.motechproject.carereporting.dao.impl;

import org.motechproject.carereporting.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDaoHibernateImpl<T> implements GenericDao<T> {

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
    public List<T> findAll() {
        return sessionFactory.getCurrentSession()
                .createCriteria(type).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(Integer id) {
        return (T) sessionFactory.getCurrentSession().get(type, id);
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
