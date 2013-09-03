package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Transactional
public interface GenericDao<T extends AbstractEntity> {

    Set<T> getAll();

    Set<T> getAllSortBy(String field);

    Set<T> getAllWithFields(String... fieldNames);

    Set<T> getAllByField(String fieldName, Object value);

    Set<T> getAllByFields(Map<String, Object> fields);

    T getById(Integer id);

    T getByIdWithFields(Integer id, String... fieldNames);

    T getByField(String fieldName, Object fieldValue);

    T getByFields(Map<String, Object> fields);

    void save(T entity);

    void update(T entity);

    void remove(T entity);

    void removeAll();

    Object executeNamedQuery(final String queryName, final Map<String, ?> queryParams);

    Object executeNamedQueryWithUniqueResult(final String queryName, final Map<String, ?> queryParams);

}
