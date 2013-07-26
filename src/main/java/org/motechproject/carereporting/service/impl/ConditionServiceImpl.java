package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ConditionDao;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConditionServiceImpl implements ConditionService {

    @Autowired
    private ConditionDao conditionDao;

    @Override
    public ConditionEntity getConditionEntityById(Integer conditionEntityId) {
        return conditionDao.getById(conditionEntityId);
    }
}
