package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.ConditionDao;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class ConditionServiceImpl extends AbstractService implements ConditionService {

    @Autowired
    private ConditionDao conditionDao;

    @Override
    public Set<ConditionEntity> findAllConditions() {
        return conditionDao.findAll();
    }

    @Override
    public ConditionEntity findConditionEntityById(Integer conditionEntityId) {
        return conditionDao.findById(conditionEntityId);
    }
}