package org.motechproject.carereporting.service.impl;

import org.hibernate.SessionFactory;
import org.motechproject.carereporting.context.ApplicationContextProvider;
import org.motechproject.carereporting.dao.CronTaskDao;
import org.motechproject.carereporting.dao.FrequencyDao;
import org.motechproject.carereporting.domain.CronTaskEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.scheduler.CronScheduler;
import org.motechproject.carereporting.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CronServiceImpl implements CronService {

    private static final String DAILY_TASK_NAME = "daily";

    private ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

    @Autowired
    private CronTaskDao cronTaskDao;

    @Autowired
    private FrequencyDao frequencyDao;

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Set<CronTaskEntity> getAllCronTasks() {
        return cronTaskDao.getAll();
    }

    @Override
    public CronTaskEntity getCronTaskByFrequencyName(String name) {
        FrequencyEntity frequencyEntity = frequencyDao.getByFrequencyName(name);
        return cronTaskDao.getByFrequency(frequencyEntity);
    }

    @Override
    public CronTaskEntity getDailyCronTask() {
        return getCronTaskByFrequencyName(DAILY_TASK_NAME);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateCronTask(CronTaskEntity cronTaskEntity) {
        cronTaskDao.update(cronTaskEntity);

        applicationContext.getBean(CronScheduler.class).updateJob(cronTaskEntity);
    }

    @Override
    public FrequencyEntity getFrequencyByName(String name) {
        return frequencyDao.getByFrequencyName(name);
    }

    @Override
    public Set<FrequencyEntity> getAllFrequencies() {
        return frequencyDao.getAllSortBy("id");
    }

    @Override
    public FrequencyEntity getFrequencyById(Integer frequencyId) {
        return frequencyDao.getById(frequencyId);
    }

    @Override
    public Date getDateDepth() {
        String sqlString = "SELECT date_depth FROM dashboard_app.date_depth";
        return (Date) sessionFactory.getCurrentSession().createSQLQuery(sqlString).uniqueResult();
    }

    @Override
    @Transactional
    public void updateDateDepth(Date newDateDepth) {
        String sqlString = "UPDATE dashboard_app.date_depth set date_depth = :dateDepth";
        sessionFactory.getCurrentSession().createSQLQuery(sqlString).setParameter("dateDepth", newDateDepth).executeUpdate();
    }

}
