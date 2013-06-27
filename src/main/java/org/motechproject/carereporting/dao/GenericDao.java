package org.motechproject.carereporting.dao;

import java.util.List;

public interface GenericDao<T> {

    List<T> findAll();

    T findById(Integer id);

    void save(T entity);

    void update(T entity);

    void remove(T entity);

    void removeAll();
}
