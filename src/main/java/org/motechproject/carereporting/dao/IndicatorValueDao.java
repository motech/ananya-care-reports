package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IndicatorValueDao extends GenericDao<IndicatorValueEntity> {

    List<IndicatorValueEntity> getIndicatorValuesForArea(Integer indicatorId, Integer areaId, Date startDate, Date endDate);

    IndicatorValueEntity getIndicatorValueClosestToDate(AreaEntity area, IndicatorEntity indicator, Date date);

    Set<IndicatorValueEntity> getValues(IndicatorEntity indicator, AreaEntity area, FrequencyEntity child, Date date);
}
