package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.util.Date;
import java.util.List;

public interface IndicatorValueDao extends GenericDao<IndicatorValueEntity> {
    List<IndicatorValueEntity> findIndicatorValuesForArea(Integer indicatorId, Integer areaId, Date earliestDate);
    IndicatorValueEntity getIndicatorValueClosestToDate(AreaEntity area, IndicatorEntity indicator, Date date);
}
