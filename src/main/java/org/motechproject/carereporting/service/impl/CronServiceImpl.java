package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.dao.CronTaskDao;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CronServiceImpl implements CronService {

    private static final String DEFAULT_TASK_NAME = "calculate_indicator_values";

    @Autowired
    private CronTaskDao cronTaskDao;

    @Override
    public CronTaskEntity getDefaultCronTask() {
        return cronTaskDao.getByName(DEFAULT_TASK_NAME);
    }

    @Override
    public CronTaskEntity getCronTaskByName(String name) {
        return cronTaskDao.getByName(name);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCronTask(CronTaskEntity cronTaskEntity) {
        cronTaskDao.update(cronTaskEntity);
    }
}
