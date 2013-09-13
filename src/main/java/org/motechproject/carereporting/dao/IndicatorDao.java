package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.IndicatorEntity;


import java.util.Set;

public interface IndicatorDao extends GenericDao<IndicatorEntity> {

    Set <IndicatorEntity> getIndicatorsByClassificationId(Integer classificationId);

    IndicatorEntity getIndicatorByName(String name);
}
