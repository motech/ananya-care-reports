package org.motechproject.carereporting.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.JDBCException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.motechproject.carereporting.dao.GenericDao;
import org.motechproject.carereporting.domain.AbstractEntity;
import org.motechproject.carereporting.exception.CareNullArgumentRuntimeException;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.exception.CareSqlRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class GenericDaoHibernateImpl<T extends AbstractEntity> implements GenericDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    private final Class<T> type;

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public GenericDaoHibernateImpl() {
        type = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public Set<T> getAll() {
        return new LinkedHashSet<T>(sessionFactory.getCurrentSession()
                .createCriteria(type).list());
    }

    @Override
    public Set<T> getAllSortBy(String field) {
        return new LinkedHashSet<T>(sessionFactory.getCurrentSession()
                .createCriteria(type).addOrder(Order.desc(field)).list());
    }

    @Override
    public Set<T> getAllWithFields(String... fieldNames) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
        if (fieldNames.length > 0) {
            for (String fieldName : fieldNames) {
                criteria = criteria.setFetchMode(fieldName, FetchMode.JOIN);
            }
        }

        return new LinkedHashSet<T>(criteria.list());
    }

    @Override
    public T getById(Integer id) {
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
    public T getByField(String fieldName, Object fieldValue) {
        return (T) getCurrentSession()
                .createCriteria(type)
                .add(Restrictions.eq(fieldName, fieldValue))
                .uniqueResult();
    }

    @Override
    public T getByFields(Map<String, Object> fields) {
        Criteria crit = getCurrentSession().createCriteria(type);
        for (Map.Entry<String, Object> entry: fields.entrySet()) {
            crit.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        return (T) crit.uniqueResult();
    }

    @Override
    public T getByIdWithFields(Integer id, String... fieldNames) {
        return getWithFields("id", id, fieldNames);
    }

    protected T getWithFields(String key, Object value, String... fieldNames) {
        if (value == null) {
            throw new CareNullArgumentRuntimeException();
        }

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(type);
        if (fieldNames.length > 0) {
            for (String fieldName : fieldNames) {
                criteria = criteria.setFetchMode(fieldName, FetchMode.JOIN);
            }
        }
        criteria.add(Restrictions.eq(key, value));

        T entity = (T) criteria.list().get(0);

        if (entity == null) {
            throw new CareResourceNotFoundRuntimeException(type, (Integer) value);
        }

        return entity;
    }

    @Override
    public void save(T entity) {
        try {
            sessionFactory.getCurrentSession().save(entity);
        } catch (JDBCException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new CareSqlRuntimeException(prepareMessageForException(e), e);
            }
        }
    }

    @Override
    public void update(T entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(entity);
            session.flush();
        } catch (JDBCException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new CareSqlRuntimeException(prepareMessageForException(e), e);
            }
        }
    }

    private String prepareMessageForException(JDBCException e) {
        String message = e.getMessage().substring(e.getMessage().indexOf("Detail"));
        String key = message.substring(message.indexOf('('), message.lastIndexOf(')'));
        String keyWithValue = key.replaceAll("[)(]", " ").trim();
        String entityName = type.getSimpleName();
        return entityName.substring(0, entityName.indexOf("Entity")) + " with " + keyWithValue + " already exists.";
    }

    @Override
    public void remove(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    @Override
    public void removeAll() {
        sessionFactory.getCurrentSession()
                .createQuery("delete from " + type.getName())
                .executeUpdate();
    }

    @Override
    public Object executeNamedQuery(final String queryName, final Map<String, ?> queryParams) {
        try {
            Query query = getCurrentSession().getNamedQuery(queryName);
            query.setProperties(queryParams);
            return query.list();
        } catch (Exception e) {
            throw new CareRuntimeException(e);
        }
    }

    @Override
    public Object executeNamedQueryWithUniqueResult(final String queryName, final Map<String, ?> queryParams) {
        try {
            Query query = getCurrentSession().getNamedQuery(queryName);
            query.setProperties(queryParams);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new CareRuntimeException(e);
        }
    }
}
