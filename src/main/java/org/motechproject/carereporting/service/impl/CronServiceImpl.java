package org.motechproject.carereporting.service.impl;

import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.dao.CronTaskDao;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.scheduler.CronScheduler;
import org.motechproject.carereporting.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CronServiceImpl implements CronService {

    private static final String DAILY_TASK_NAME = "daily";

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    @Autowired
    private CronTaskDao cronTaskDao;

    @Override
    public Set<CronTaskEntity> getAllCronTasks() {
        return cronTaskDao.getAll();
    }

    @Override
    public CronTaskEntity getCronTaskByName(String name) {
        return cronTaskDao.getByName(name);
    }

    @Override
    public CronTaskEntity getDailyCronTask() {
        return cronTaskDao.getByName(DAILY_TASK_NAME);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCronTask(CronTaskEntity cronTaskEntity) {
        cronTaskDao.update(cronTaskEntity);

        applicationContext.getBean(CronScheduler.class).updateJob(cronTaskEntity);
    }

}
