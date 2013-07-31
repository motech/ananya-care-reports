package org.motechproject.carereporting.service.impl;

import org.hibernate.Hibernate;
import org.motechproject.carereporting.dao.CronTaskDao;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CronServiceImpl implements CronService {

    @Autowired
    private CronTaskDao cronTaskDao;

    @Override
    public Set<CronTaskEntity> getAllCronTasks() {
        Set<CronTaskEntity> allCronTasks = cronTaskDao.getAll();
        for (CronTaskEntity cronTaskEntity: allCronTasks) {
            Hibernate.initialize(cronTaskEntity.getIndicator().getComplexCondition().getConditions());
        }
        return allCronTasks;
    }

    @Override
    public CronTaskEntity getCronTaskByIndicatorId(Integer indicatorId) {
        return cronTaskDao.getByIndicatorId(indicatorId);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCronTask(CronTaskEntity cronTaskEntity) {
        cronTaskDao.update(cronTaskEntity);
    }

    @Override
    public void createCronTask(CronTaskEntity cronTaskEntity) {
        cronTaskDao.save(cronTaskEntity);
    }

}
