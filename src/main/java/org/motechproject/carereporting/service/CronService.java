package org.motechproject.carereporting.service;

import org.motechproject.carereporting.domain.CronTaskEntity;

import java.util.Set;

public interface CronService {

    Set<CronTaskEntity> getAllCronTasks();

    CronTaskEntity getCronTaskByIndicatorId(Integer indicatorId);

    void updateCronTask(CronTaskEntity cronTaskEntity);

    void createCronTask(CronTaskEntity cronTaskEntity);

}
