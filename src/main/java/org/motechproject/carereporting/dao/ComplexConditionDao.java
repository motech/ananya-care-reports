package org.motechproject.carereporting.dao;

import org.motechproject.carereporting.domain.ComplexConditionEntity;

import java.util.List;

public interface ComplexConditionDao extends GenericDao<ComplexConditionEntity> {

    List<ComplexConditionEntity> getAllByIndicatorId(Integer indicatorId);

}
