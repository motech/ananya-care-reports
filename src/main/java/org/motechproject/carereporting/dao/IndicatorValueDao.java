package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.util.Date;
import java.util.List;

public interface IndicatorValueDao extends GenericDao<IndicatorValueEntity> {
    List<IndicatorValueEntity> findIndicatorValuesForArea(Integer indicatorId, Integer areaId, Date earliestDate);
}
