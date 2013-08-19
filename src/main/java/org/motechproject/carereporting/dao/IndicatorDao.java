package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.IndicatorEntity;


import java.util.Set;

public interface IndicatorDao extends GenericDao<IndicatorEntity> {

    Set <IndicatorEntity> getIndicatorsByCategoryId(Integer categoryId);

    IndicatorEntity getIndicatorByName(String name);
}
