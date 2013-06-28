package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AbstractEntity;

import java.util.List;

public interface GenericDao<T extends AbstractEntity> {

    List<T> findAll();

    T findById(Integer id);

    void save(T entity);

    void update(T entity);

    void remove(T entity);

    void removeAll();
}
