package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.ComplexConditionEntity;

import java.util.Set;

public interface ComplexConditionDao extends GenericDao<ComplexConditionEntity> {

    Set<ComplexConditionEntity> getAllByIndicatorId(Integer indicatorId);

}
