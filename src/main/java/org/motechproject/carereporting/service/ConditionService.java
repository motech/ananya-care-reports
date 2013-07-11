package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.ConditionEntity;

import java.util.Set;

public interface ConditionService {

    Set<ConditionEntity> findAllConditions();

    ConditionEntity findConditionEntityById(Integer conditionEntityId);
}